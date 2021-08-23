package com.faskn.app.weatherapp.domain.model

import android.os.Parcelable
//@Parcelize
//@JsonClass(generateAdapter = true)
data class Coord(

    //@Json(name = "lon")
    val lon: Double?,

    //@Json(name = "lat")
    val lat: Double?
) //: Parcelable
