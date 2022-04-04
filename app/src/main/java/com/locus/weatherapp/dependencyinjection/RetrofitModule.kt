package com.locus.weatherapp.dependencyinjection

import android.content.Context
import com.locus.weatherapp.NetworkHelper
import com.locus.weatherapp.model.api.WeatherAPI
import com.locus.weatherapp.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private val BASE_URL = "https://pro.openweathermap.org/data/2.5/forecast/hourly/"

    @Singleton
    @Provides
    fun provideWeatherApi(): WeatherAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    @Provides
    fun provideOkHttpClient(logger:HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logger)
        .addNetworkInterceptor(Interceptor { chain ->
            val request: Request = chain.request().newBuilder() // .addHeader(Constant.Header, authToken)
                    .build()
            chain.proceed(request)
        })
        .build()

    @Provides
    fun provideHttpLoggingInterceptor():HttpLoggingInterceptor {
        val interceptor  = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }




    @Singleton
    @Provides
    fun provideRepository(weatherAPI: WeatherAPI): MainRepository = MainRepository(weatherAPI)

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context):NetworkHelper = NetworkHelper(context)

}
