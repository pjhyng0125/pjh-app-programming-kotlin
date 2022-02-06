package com.pjh.seoultoilet

import android.graphics.Bitmap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class MyItem(val _position: LatLng, val _title: String, val _snippet: String, val _icon: BitmapDescriptor): ClusterItem {
    override fun getSnippet(): String {
        return _snippet
    }

    override fun getTitle(): String {
        return _title
    }

    override fun getPosition(): LatLng {
        return _position
    }

    fun getIcon(): BitmapDescriptor {
        return _icon
    }

    override fun equals(other: Any?): Boolean {
        if (other is MyItem) {
            return (other.position.latitude == position.latitude
                    && other.position.longitude == position.longitude
                    && other.title == _title
                    && other.snippet == _snippet
                    )
        }
        return false
    }

    // equals 오버로이드 시, hashCode 도 반드시 오버라이드 필요
    override fun hashCode(): Int {
        var hash = _position.longitude.hashCode() * 31
        hash = hash * 31 + _position.longitude.hashCode()
        hash = hash * 31 + title.hashCode()
        hash = hash * 31 + snippet.hashCode()
        return hash
    }
}