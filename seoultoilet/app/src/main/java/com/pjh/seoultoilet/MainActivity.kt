package com.pjh.seoultoilet

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_bar.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
    // 런타임 권한 필요 permission 목록
    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION)

    val REQ_PERMISSION_CD = 1
    val DEFAULT_ZOOM_LEVEL = 17f
    // LatLng : 위도와 경도를 가지는 클래스
    val CITY_HALL_LOCATION = LatLng(37.5662952, 126.9779450999994)

    var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapView.onCreate(savedInstanceState)
        if (hasPermissions()) {
            initMap()
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQ_PERMISSION_CD)
        }

        myLocationButton.setOnClickListener {
            onMyLocationButtonClick()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        initMap()
    }

    // 앱 사용 권한 체크
    fun hasPermissions(): Boolean {
        for (pms in PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, pms) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    // ClusterManager 변수 선언
    var clusterManager: ClusterManager<MyItem>? = null

    // ClusterRenderer 변수 선언
    var clusterRenderer: ClusterRenderer? = null

    @SuppressLint("MissingPermission")
    fun initMap() {
        mapView.getMapAsync {
            // ClusterManager 객체 초기화
            clusterManager = ClusterManager(this, it)
            clusterRenderer = ClusterRenderer(this, it, clusterManager!!)

            // OnCameraIdleListener 와 OnMarkerClickListener 를 clusterManager 로 지정
            it.setOnCameraIdleListener(clusterManager)
            it.setOnMarkerClickListener(clusterManager)

            // google map 객체 저장
            googleMap = it
            it.uiSettings.isMyLocationButtonEnabled = false
            when {
                hasPermissions() -> {
                    // 위치 사용 권한 있는 경우
                    // 현위치 표시 활성화
                    it.isMyLocationEnabled = true
                    // 현위치 카메라 이동
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL))
                }
                else -> {
                    // 디폴트 위치 : 시청
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(CITY_HALL_LOCATION, DEFAULT_ZOOM_LEVEL))
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getMyLocation(): LatLng {
        val locationProvider: String = LocationManager.GPS_PROVIDER
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // 최종 업데이트 위치 반환
        val lastKnownLocation: Location? = locationManager.getLastKnownLocation(locationProvider)
        if (lastKnownLocation != null) {
            // 위도 경로 객체 반환
            return LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
        } else {
            // 디폴트 위치 : 시청
            return CITY_HALL_LOCATION
        }
    }

    // 현재 위치 버튼 클릭한 경우
    fun onMyLocationButtonClick() {
        when {
            hasPermissions() -> googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL))
            else -> {
                Toast.makeText(applicationContext, "위치사용권한 설정에 동의해주세요", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    /* Google Map - 공공 화장실 데이터 연동 */
    // 서울 열린 데이터 광장 발급 API 키
    val API_KEY = "서울열린데이터광장발급API키"
    // 앱 비활성화 시 백드라운드 작업 취소 변수 선언
    var task: ToiletReadTask? = null
    // 서울시 화장실 정보 집합 저장 Array
    var toiletList = JSONArray()
    // JsonObject 를 키로 MyItem 객체를 저장할 맵
    val itemMap = mutableMapOf<JSONObject, MyItem>()

    // 화장실 이미지 Bitmap
    val bitmap by lazy {
        val drawable = resources.getDrawable(R.drawable.restroom_sign) as BitmapDrawable
        Bitmap.createScaledBitmap(drawable.bitmap, 64, 64, false)
    }

    // JSONArray 병합 확장함수 사용
    fun JSONArray.merge(anotherArray: JSONArray) {
        for (i in 0 until anotherArray.length()) {
            this.put(anotherArray.get(i))
        }
    }

    // 화장실 정보 JSONObject 반환
    fun readData(startIdx: Int, lastIdx: Int): JSONObject {
        val url = URL("http://openAPI.seoul.go.kr:8088" + "/${API_KEY}/json/SearchPublicToiletPOIService/${startIdx}/${lastIdx}")
        val con = url.openConnection()
        val data = con.getInputStream().readBytes().toString(charset("UTF-8"))
        return JSONObject(data)
    }

    // 화장실 데이터 로드 AsyncTask
    inner class ToiletReadTask : AsyncTask<Void, JSONArray, String>() {
        override fun onPreExecute() {
            googleMap?.clear()
            toiletList = JSONArray()
            itemMap.clear()
        }

        override fun doInBackground(vararg params: Void?): String {
            // 서울시 데이터는 최대 1000 개씩 가져올수 있기 때문에
            // step 만큼 startIndex 와 lastIndex 값을 변경하며 여러번 호출해야 함.
            val step = 1000
            var startIndex = 1
            var lastIndex = step
            var totalCount = 0
            do {
                // 백그라운드 작업이 취소된 경우 루프를 빠져나간다.
                if (isCancelled) break
                // totalCount 가 0 이 아닌 경우 최초 실행이 아니므로 step 만큼 startIndex 와 lastIndex 를 증가
                if (totalCount != 0) {
                    startIndex += step
                    lastIndex += step
                }
                // startIndex, lastIndex 로 데이터 조회
                val jsonObject = readData(startIndex, lastIndex)
                // totalCount 를 가져온다.
                totalCount = jsonObject.getJSONObject("SearchPublicToiletPOIService")
                    .getInt("list_total_count")
                // 화장실 정보 데이터 집합을 가져온다.
                val rows =
                    jsonObject.getJSONObject("SearchPublicToiletPOIService").getJSONArray("row")
                // 기존에 읽은 데이터와 병합
                toiletList.merge(rows)
                // UI 업데이트를 위해 progress 발행
                publishProgress(rows)
            } while (lastIndex < totalCount) // lastIndex 가 총 개수보다 적으면 반복한다
            return "complete"
        }

        override fun onProgressUpdate(vararg values: JSONArray?) {
            val arr = values[0]
            arr?.let {
                for (i in 0 until arr.length()) {
                    addMarkers(arr.getJSONObject(i))
                }
            }
            clusterManager?.cluster()
        }

        override fun onPostExecute(result: String?) {
            val textList = mutableListOf<String>()
            for (i in 0 until toiletList.length()) {
                val toilet = toiletList.getJSONObject(i)
                textList.add(toilet.getString("FNAME"))
            }
            val adapter = ArrayAdapter<String>(
                this@MainActivity,
                android.R.layout.simple_dropdown_item_1line, textList
            )
            searchBar.autoCompleteTextView.threshold = 1
            searchBar.autoCompleteTextView.setAdapter(adapter)
        }
    }

    fun JSONArray.findByChildProperty(propertyName: String, value: String): JSONObject? {
        for (i in 0 until length()) {
            val obj = getJSONObject(i)
            if (value == obj.getString(propertyName)) return obj
        }
        return null
    }

    override fun onStart() {
        super.onStart()
        task?.cancel(true)
        task = ToiletReadTask()
        task?.execute()

        // 검색 버튼 클릭 이벤트 추가
        searchBar.imageView.setOnClickListener {
            val keyword = searchBar.autoCompleteTextView.text.toString()
            if (TextUtils.isEmpty(keyword)) return@setOnClickListener

            toiletList.findByChildProperty("FNAME", keyword)?.let {
                val myItem = itemMap[it]
                val marker = clusterRenderer?.getMarker(myItem)
                marker?.showInfoWindow()
                googleMap?.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(it.getDouble("Y_WGS84"), it.getDouble("X_WGS84")), DEFAULT_ZOOM_LEVEL))
                        clusterManager?.cluster()
            }
            searchBar.autoCompleteTextView.setText("")
        }
    }

    override fun onStop() {
        super.onStop()
        task?.cancel(true)
        task = null
    }

    fun addMarkers(toilet: JSONObject) {
//        googleMap?.addMarker(
//            MarkerOptions()
//                .position(LatLng(toilet.getDouble("Y_WGS84"), toilet.getDouble("X_WGS84")))
//                .title(toilet.getString("FNAME"))
//                .snippet(toilet.getString("ANAME"))
//                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
//        )

        val item = MyItem(
            LatLng(toilet.getDouble("Y_WGS84"), toilet.getDouble("X_WGS84")),
            toilet.getString("FNAME"),
            toilet.getString("ANAME"),
            BitmapDescriptorFactory.fromBitmap(bitmap)
        )
        // clusterManager 를 이용해 마커 추가
        clusterManager?.addItem(
            MyItem(
                LatLng(toilet.getDouble("Y_WGS84"), toilet.getDouble("X_WGS84")),
                toilet.getString("FNAME"),
                toilet.getString("ANAME"),
                BitmapDescriptorFactory.fromBitmap(bitmap)
            )
        )
        // 아이템맵에 toilet 객체를 키로 MyItem 객체 저장
        itemMap.put(toilet, item)
    }
}