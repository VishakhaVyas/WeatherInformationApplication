package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.example.myapplication.model.ForcastDetail
import com.example.myapplication.model.HistoryItem
import com.example.myapplication.RestService.NetworkService
import com.example.myapplication.model.CurrentWeatherResponse
import com.google.gson.Gson
import java.util.HashMap

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