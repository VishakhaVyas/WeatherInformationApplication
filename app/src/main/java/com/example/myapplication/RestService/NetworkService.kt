package com.example.myapplication.RestService

import android.net.Uri
import okhttp3.*
import java.io.IOException
import java.util.*

class NetworkService() {
    //val INSTANCE: NetworkService()
    companion object {
        val instance = NetworkService()
    }

    init {
        buildClient()
    }

    private var okHttpClient: OkHttpClient? = null
    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    //private val BASE_URL = "https://api.openweathermap.org/data/2.5/onecall"
    //private val BASE_URL = "https://api.openweathermap.org/data/2.5/weather"
    //private val BASE_URL = "https:// api.openweathermap.org/data/2.5/forecast"//?lat=35&lon=139&appid={API key}
    val ACCESS_KEY = "40055cf8441131eaf6ba91e649852a56"
    /**
     * Method to build and return an OkHttpClient so we can set/get
     * headers quickly and efficiently.
     * @return OkHttpClient
     */
     private fun buildClient(): OkHttpClient? {
        if (okHttpClient != null) return okHttpClient

        okHttpClient = OkHttpClient()
         return okHttpClient
    }

    fun get(endPoint : String?, queryParam: HashMap<String, String>?, callback:(error:Exception?, response:String?)->Unit){
        val builder =
            Uri.parse(BASE_URL + endPoint)
                .buildUpon()
        if (queryParam != null && !queryParam.isEmpty()) {
            for ((key, value) in queryParam.entries) {
                builder.appendQueryParameter(key, value)
            }
        }
        val request = Request.Builder()
            .url(builder.build().toString())
            .addHeader("Accept-Version", "v1")
            .addHeader("Authorization", "Client-ID $ACCESS_KEY")
            .get()
            .build()

//        val response = client.newCall(request).execute()

        okHttpClient?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(e, null)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        throw IOException("Unexpected code $response")
                    }
                    callback(null, response.body!!.string())
                }
            }
        })
    }

}