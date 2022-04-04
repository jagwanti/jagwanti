package com.locus.weatherapp.model

sealed class Resource<T>(var status:Status, var data:T? , var message:String?) {
    class Error<T>(message: String?):Resource<T>( Status.SUCCESS,null,message){}
    class Success<T>(data: T?):Resource<T>(Status.ERROR,data,null){}
    class Loading<T>(message: String?):Resource<T>(Status.LOADING,null,message){}
}
