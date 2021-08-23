package com.faskn.app.weatherapp.domain.model


//@Parcelize
//@JsonClass(generateAdapter = true)
data class Main(

   // @Json(name = "temp")
    val temp: Double?,

    //@Json(name = "temp_min")
    var temp_min: Double?,

    //@Json(name = "grnd_level")
    val grndLevel: Double?,

    //@Json(name = "temp_kf")
    val tempKf: Double?,

    //@Json(name = "humidity")
    val humidity: Int?,

    //@Json(name = "pressure")
    val pressure: Double?,

    //@Json(name = "sea_level")
    val seaLevel: Double?,

    //@Json(name = "temp_max")
    var temp_max: Double?
)/* : Parcelable {

    fun getTempString(): String {
        return temp.toString().substringBefore(".") + "°"
    }

    fun getHumidityString(): String {
        return humidity.toString() + "°"
    }

    fun getTempMinString(): String {
        return tempMin.toString().substringBefore(".") + "°"
    }

    fun getTempMaxString(): String {
        return tempMax.toString().substringBefore(".") + "°"
    }
}
*/