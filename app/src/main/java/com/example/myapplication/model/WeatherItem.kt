package com.example.myapplication.model


//@Parcelize
//@JsonClass(generateAdapter = true)
data class WeatherItem(

    //@Json(name = "icon")
    val icon: String?,

    //@Json(name = "description")
    val description: String?,

    //@Json(name = "main")
    val main: String?,

    //@Json(name = "id")
    val id: Int?
) /*: Parcelable {

    fun getDescriptionText(): String? {
        return description?.capitalize()
    }
}*/
