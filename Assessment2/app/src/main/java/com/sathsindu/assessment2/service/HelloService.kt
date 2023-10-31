package com.sathsindu.assessment2.service

import com.sathsindu.assessment2.HelloResponse
import retrofit2.Call
import retrofit2.http.GET

interface HelloService {
    @GET("hello")
    fun hello(): Call<HelloResponse?>?
}