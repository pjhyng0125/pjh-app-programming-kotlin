package com.pjh.seoultoilet

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class ClusterRenderer(context: Context?, map: GoogleMap?, clusterManager: ClusterManager<MyItem>): DefaultClusterRenderer<MyItem>(context, map, clusterManager) {
    init {
        // 초기화 : ClusterManger 클래스의 renderer 를 클래스 자신으로 지정
        clusterManager?.renderer = this
    }

    override fun onBeforeClusterItemRendered(item: MyItem?, markerOptions: MarkerOptions?) {
        markerOptions?.icon(item?.getIcon())
        markerOptions?.visible(true)
    }
}