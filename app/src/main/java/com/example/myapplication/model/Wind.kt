package com.faskn.app.weatherapp.domain.model

import android.os.Parcelable

//@Parcelize
//@JsonClass(generateAdapter = true)
data class Wind(

    //@Json(name = "deg")
    val deg: Double?,

    //@Json(name = "speed")
    val speed: Double?
) //: Parcelable
