package com.levins.junky

import com.google.android.gms.maps.model.LatLng


data class ItemData(
    val title:String,
    val description:String,
    val locationDescription:String?,
    val latLng: LatLng
)