package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.CurrentWeatherResponse

class HistoryActivity : AppCompatActivity() {
    //lateinit var database : WeatherDatabase
    private val weatherHistoryList: MutableList<CurrentWeatherResponse>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)


        val adapter = weatherHistoryList?.let { AdapterHistory(it) }
        recyclerview.adapter = adapter

    }

  /*  fun getData(view : View){
        database.currentWeatherDao().getCurrentWeather().observe(this, Observer {
            Log.d("create database", it.toString())
        })
    }*/

}