package com.locus.weatherapp.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.locus.weatherapp.model.ForecastData
import com.locus.weatherapp.model.Resource
import com.locus.weatherapp.model.Status
import com.locus.weatherapp.model.WeatherResponse
import com.locus.weatherapp.repository.MainRepository
import com.locus.weatherapp.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CityViewModel @Inject constructor(var repository:MainRepository, var networkHelper: NetworkHelper) : ViewModel() {
    private var job = SupervisorJob()
    private val viewModelScope : CoroutineScope
    get() {
        return CoroutineScope(Dispatchers.IO + job )
    }

    private val weatherResponse = MutableLiveData<Resource<WeatherResponse>>()
    var detailResponse : ObservableField<ForecastData> = ObservableField()
    var tempVal : ObservableField<Int> = ObservableField()
    var tempFeelsVal : ObservableField<String> = ObservableField()
    var weatherVal : ObservableField<String> = ObservableField()
    var weatherDescVal : ObservableField<String> = ObservableField()

    val weatherData: LiveData<Resource<WeatherResponse>>
        get() = weatherResponse



    override fun onCleared() {
        try {
            job.cancel()
        }catch (e: Exception){
            print(e.message)
        }catch (e: Throwable){
            print(e.message)
        }
        super.onCleared()
    }

    fun getCityData( name:String) {
        viewModelScope.launch {
            weatherResponse.postValue(Resource.Loading("Please wait"))
            repository.getWeatherData(name, com.locus.weatherapp.BuildConfig.API_KEY).let {
                if(networkHelper.isNetworkConnected()) {
                    if (it.status == Status.SUCCESS) {
                        weatherResponse.postValue(Resource.Success(it.data))
                    } else weatherResponse.postValue(Resource.Error(it.message))
                }else weatherResponse.postValue(Resource.Error("Please connect to Network and try again"))
            }
        }
    }
}