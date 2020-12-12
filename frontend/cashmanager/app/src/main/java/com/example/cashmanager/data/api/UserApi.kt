package com.example.cashmanager.data.api

import com.example.cashmanager.data.model.Account
import com.example.cashmanager.data.model.User
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface UserApi {
    @GET("/user")
    fun read(): Call<Account>

    @Headers("Content-Type: application/json")
    @POST("/register")
    fun register(@Body user: RequestBody): Call<User>

    @Headers("Content-Type: application/json")
    @POST("/login")
    fun login(@Body user: RequestBody): Call<User>

    @PUT("/user")
    fun update(@Body user: Account): Call<Account>

    @DELETE("/user")
    fun delete(): Call<User>
}