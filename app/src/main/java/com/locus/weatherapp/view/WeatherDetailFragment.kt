package com.locus.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.locus.weatherapp.R
import com.locus.weatherapp.databinding.DetailFragmentBinding
import com.locus.weatherapp.model.ForecastData
import com.locus.weatherapp.model.Status
import com.locus.weatherapp.viewmodel.CityViewModel
import dagger.Provides
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherDetailFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherDetailFragment()
    }

    private lateinit var itemBinding: DetailFragmentBinding
    private lateinit var viewModel: CityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        itemBinding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        return itemBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[CityViewModel::class.java]
        viewModel.detailResponse.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (sender is ObservableField<*> && sender.get() != null) {
                    val data = sender.get() as ForecastData
                    viewModel.tempVal.set(data.main.temp)
                    viewModel.tempFeelsVal.set(
                        resources.getString(
                            R.string.temp_feels,
                            data.main.feels_like
                        )
                    )
                    viewModel.weatherVal.set(data.weather.main)
                    viewModel.weatherDescVal.set(data.weather.description)
                }
            }
        })
    }
}