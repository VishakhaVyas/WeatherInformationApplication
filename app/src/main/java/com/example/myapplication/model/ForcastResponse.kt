package com.example.myapplication.model

import com.faskn.app.weatherapp.domain.model.Coord

data class ForcastResponse(
    val cod: String,
    val message: String,
    val cnt: String,
    val list: ArrayList<CurrentWeatherResponse>,
    val city: City



)

data class City(
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: String,
    val timezone: String,
    val sunrise: String,
    val sunset: String
)