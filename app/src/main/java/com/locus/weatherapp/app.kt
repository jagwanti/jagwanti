package com.locus.weatherapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NetworkHelper (@ApplicationContext private val context: Context){
fun isNetworkConnected (): Boolean {
    var result = false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
    }

    return result
}
}

class Utils{
    companion object{
        fun parseDateString(date:String):String{
           val tempDate = SimpleDateFormat("yyyy-mm-dd HH:mm:ss.SSSSSS").parse(date)
            val cal = Calendar.getInstance()
            cal.time = tempDate
            return getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK))
        }
        fun getDayOfWeek(day:Int):String{
           var weekday =""
            when(day){
                1 -> weekday ="Sunday"
                2 -> weekday ="Monday"
                3 -> weekday ="Tuesday"
                4 -> weekday ="Wednesday"
                5 -> weekday ="Thursday"
                6 -> weekday ="Friday"
                7 -> weekday ="Saturday"
           }
            return weekday
        }
    }
}
