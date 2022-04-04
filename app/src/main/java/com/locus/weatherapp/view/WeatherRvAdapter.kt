package com.locus.weatherapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.locus.weatherapp.R
import com.locus.weatherapp.Utils
import com.locus.weatherapp.model.ForecastData
import com.locus.weatherapp.viewmodel.CityViewModel

class WeatherRvAdapter (private val dataList:ArrayList<ForecastData>, private val viewModal:CityViewModel): RecyclerView.Adapter<WeatherRvAdapter.WeatherViewHolder>() {

    lateinit var mContext :Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        mContext = parent.context
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.weather_item_row, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val data = dataList[position]
        holder.weatherTv.text= data.weather.main
        holder.tempTv.text = mContext.resources.getString(R.string.temp_title,data.main.temp)
        holder.dayTv.text = Utils.parseDateString(data.dt_txt)
        holder.itemView.setOnClickListener{
            viewModal.detailResponse.set(data)
            (mContext as MainActivity).replaceFragments(WeatherDetailFragment::class.java) }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val weatherTv = itemView.findViewById<TextView>(R.id.weather_tv)
        val tempTv = itemView.findViewById<TextView>(R.id.temp_tv)
        val dayTv = itemView.findViewById<TextView>(R.id.day_tv)
    }
}