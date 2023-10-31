package com.sathsindu.assessment2.service

import com.sathsindu.assessment2.LoginRequest
import com.sathsindu.assessment2.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("authenticate")
    fun login(@Body loginRequest: LoginRequest?): Call<LoginResponse?>?
}