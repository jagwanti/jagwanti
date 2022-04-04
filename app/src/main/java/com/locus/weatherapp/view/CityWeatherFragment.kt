package com.locus.weatherapp.view

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.locus.weatherapp.R
import com.locus.weatherapp.model.ForecastData
import com.locus.weatherapp.model.Status
import com.locus.weatherapp.viewmodel.CityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityWeatherFragment : Fragment() {

    companion object {
        fun newInstance() = CityWeatherFragment()
    }

    private lateinit var viewModel: CityViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar:ProgressBar
    private lateinit var mView: View
    private var dataList:ArrayList<ForecastData> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        mView = inflater.inflate(R.layout.city_weather_fragment,container,false)
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[CityViewModel::class.java]
        val cityName  = arguments?.getString("CityName")?:""
        if(!TextUtils.isEmpty(cityName)) {
                viewModel.getCityData(cityName)
                recyclerView = mView.findViewById(R.id.weather_iv)
                progressBar = mView.findViewById(R.id.progress_bar)
                val layoutManager = LinearLayoutManager(requireActivity())
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = WeatherRvAdapter(dataList,viewModel)
                viewModel.weatherData.observe(viewLifecycleOwner, {
                    when (it.status) {
                        Status.LOADING -> {
                            //show weather data on screen in recycler view and add listner. when clicked open the details fragment
                            progressBar.visibility = View.VISIBLE
                            recyclerView.visibility = View.GONE
                        }
                        Status.SUCCESS -> {
                            progressBar.visibility = View.GONE
                            recyclerView.visibility = View.VISIBLE
                            //show weather data on screen in recycler view and add listner. when clicked open the details fragment
                            dataList = it.data?.list ?: ArrayList()
                            (recyclerView.adapter)?.notifyDataSetChanged()
                        }
                        Status.ERROR -> {
                            progressBar.visibility = View.GONE
                            if(TextUtils.isEmpty(it.message)){
                                it.message = resources.getString(R.string.error_msg)
                            }
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
//                            requireActivity().finish()
                        }
                    }

                })
            }else return
    }
}