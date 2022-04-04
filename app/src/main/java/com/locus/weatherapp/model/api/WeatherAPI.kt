package com.locus.weatherapp.model.api

import com.locus.weatherapp.model.Resource
import com.locus.weatherapp.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherAPI {
        @GET(".")
        suspend fun getWeatherData(@Query("q") cityName:String, @Query("appid") appId: String): Resource<WeatherResponse>
}