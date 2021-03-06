package com.example.myapplication.model

import com.faskn.app.weatherapp.domain.model.Coord
import com.faskn.app.weatherapp.domain.model.Main
import com.faskn.app.weatherapp.domain.model.Sys
import com.faskn.app.weatherapp.domain.model.Wind
import java.text.DateFormat
import java.util.*

//@Parcelize
//@JsonClass(generateAdapter = true)
data class CurrentWeatherResponse(

    //@Json(name = "visibility")
    val visibility: Int? = null,

    //@Json(name = "timezone")
    val timezone: Int? = null,

   // @Json(name = "main")
    val main: Main? = null,

    //@Json(name = "clouds")
    val clouds: Clouds? = null,

    //@Json(name = "sys")
    val sys: Sys? = null,

   // @Json(name = "dt")
    val dt: Int? = null,

   // @Json(name = "coord")
    val coord: Coord? = null,

   // @Json(name = "weather")
    val weather: List<WeatherItem?>? = null,

   // @Json(name = "name")
    val name: String? = null,

    //@Json(name = "cod")
    val cod: Int? = null,

    //@Json(name = "id")
    val id: Int? = null,

    //@Json(name = "base")
    val base: String? = null,

    //@Json(name = "wind")
    val wind: Wind? = null,

    val dt_txt: String? = null
)/* : Parcelable*/
