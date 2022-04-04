package com.locus.weatherapp.model

import kotlin.collections.ArrayList


data class WeatherResponse(var cod:String,var message : Int, var cnt: Int, var list:ArrayList<ForecastData>)

data class ForecastData(var dt:Long,var main:MainWeatherData,var weather: Weather,var dt_txt:String)

data class MainWeatherData(var temp: Int, var feels_like: Int,var temp_min: Int,var temp_max: Int,
                           var pressure: Int,var sea_level: Int,var grnd_level: Int,var humidity: Int,var temp_kf: Int)

data class Weather(var id: Int, var main: String, var description:String, var icon: String)

