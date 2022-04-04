package com.locus.weatherapp.repository

import android.util.Log
import com.locus.weatherapp.model.Resource
import com.locus.weatherapp.model.WeatherResponse
import com.locus.weatherapp.model.api.WeatherAPI
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

class MainRepository  @Inject constructor(val weatherAPI: WeatherAPI){
    suspend fun getWeatherData(cityName:String, appId: String): Resource<WeatherResponse> {
        return try {
            return weatherAPI.getWeatherData(cityName,appId)
        }catch (e:Exception){
            Log.e("MainRepository","weatherAPI $e")
            Resource.Error("An $e occurred")
        }
    }
}