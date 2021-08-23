package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.RestService.NetworkService
import com.example.myapplication.model.CurrentWeatherResponse
import com.example.myapplication.model.ForcastResponse
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*


class WeatherFragment : Fragment() {
    var recyclerView: RecyclerView?=null
    var gridLayoutManager: GridLayoutManager? = null

    val listItems: MutableList<CurrentWeatherResponse> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_weather_info, container, false)
        recyclerView = view.findViewById(R.id.recyclerForecast)
        gridLayoutManager = GridLayoutManager(activity?.applicationContext, 2,
            RecyclerView.VERTICAL, false)
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(true)

        val lat = arguments?.getString("lat")
        val lon = arguments?.getString("lon")

        val params = HashMap<String, String>()
        params["lat"] = lat.toString()
        params["lon"] = lon.toString()
        params["appid"] = "40055cf8441131eaf6ba91e649852a56"
        //var forcastDetail  : ForcastResponse ?= null
        NetworkService.instance.get("forecast", params) { error, response ->
            if (error == null) {
                //parse response
                println(response)
                var gson = Gson()
                val forcastDetail = gson.fromJson(response, ForcastResponse::class.java)

                for (i in 1..20) {
                    var  jsonObject = forcastDetail.list[i]
                    listItems.add(jsonObject)
                }
                activity?.runOnUiThread {
                    recyclerView?.adapter = RecyclerViewAdapter(forcastDetail.list.subList(0,6))
                }

            } else {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

        }

        return view
    }
}





class RecyclerViewAdapter(private val weatherList: MutableList<CurrentWeatherResponse>)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast,parent,false)

        return ViewHolder(itemView)

    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewDayOfWeek.text = getDay(weatherList.get(position).dt_txt.toString())

        holder.textViewTemp.text =  String.format("%.0f", weatherList.get(position).main?.temp?.minus(273.15)) + "\u2103"
        holder.txtMinString.text = String.format("%.0f", weatherList.get(position).main?.temp_min?.minus(273.15))+ "\u2103"
        holder.txtMaxString.text = String.format("%.0f", weatherList.get(position).main?.temp_max?.minus(273.15))+ "\u2103"
    }


    override fun getItemCount(): Int {
        // number of items in the data set held by the adapter
        return weatherList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textViewDayOfWeek: TextView = itemView.findViewById(R.id.textViewDayOfWeek)
        val textViewTemp: TextView = itemView.findViewById(R.id.textViewTemp)
        val txtMinString: TextView = itemView.findViewById(R.id.txtMinString)
        val txtMaxString: TextView = itemView.findViewById(R.id.txtMaxString)
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    /**
     * This method is getting day of week from given date
     */
    fun getDay(date: String): String?
    {
        val week = Calendar.getInstance(TimeZone.getTimeZone(date)).get(Calendar.DAY_OF_WEEK)
        var dayOfWeek: String? = null
        when (week) {
            1 -> dayOfWeek = "Sunday"
            2 -> dayOfWeek = "Monday"
            3 -> dayOfWeek = "Tuesday"
            4 -> dayOfWeek = "Wednesday"
            5 -> dayOfWeek = "Thursday"
            6 -> dayOfWeek = "Friday"
            7 -> dayOfWeek = "Saturday"
        }
        return dayOfWeek
    }
}

