package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.RestService.NetworkService
import com.example.myapplication.databinding.FragmentCurrentAddressBinding
import com.example.myapplication.model.CurrentWeatherResponse
import com.example.myapplication.model.WeatherItem
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class CurrentAddressFragment() : Fragment() {
    var  textViewTemperature: TextView ?= null
    var imageViewWeatherIcon: ImageView ?= null
    var  textViewWeatherMain: TextView ?= null
    var  textViewHumidity: TextView ?= null
    var id : Int ?= null
    lateinit var mContext : Context

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_current_address, container, false)
        // mContext = context
        val currentLoc = arguments?.getString("CURRENT_LOCATION")
        val lat = arguments?.getString("lat")
        val lon = arguments?.getString("lon")
        view.findViewById<TextView>(R.id.textCity).apply {
            text = currentLoc
        }

        textViewTemperature = view.findViewById(R.id.textViewTemperature)
        imageViewWeatherIcon = view.findViewById(R.id.imageViewWeatherIcon)
        textViewHumidity = view.findViewById(R.id.textViewHumidity)

        val params = HashMap<String, String>()
        params["lat"] = lat.toString()
        params["lon"] = lon.toString()
        params["appid"] = "40055cf8441131eaf6ba91e649852a56"


        NetworkService.instance.get("weather", params) { error, response ->
            if (error == null) {
                println(response)
                val gson = Gson()
                val weather =  gson.fromJson(response, CurrentWeatherResponse::class.java)
                // database.currentWeatherDao().insertCurrentWeather(weather)
                // TODO: 8/18/2021 Add null checks before setting UI
                //database = WeatherDatabase.getInstance(mContext?.applicationContext)

                /*GlobalScope.launch {
                    database.currentWeatherDao().insertCurrentWeather(weather)

                }*/


                activity?.runOnUiThread {
                    textViewTemperature?.setText(String.format("%.0f", weather.main?.temp?.minus(273.15)) + "\u2103")
                    imageViewWeatherIcon?.setImageResource(R.drawable.ic_baseline_cloud_24)
                    textViewHumidity?.setText(weather.main?.humidity.toString())
                }

            } else {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
        textViewTemperature?.text = ""
        textViewHumidity?.text = ""
        textViewWeatherMain?.text = ""

        return view
    }

}