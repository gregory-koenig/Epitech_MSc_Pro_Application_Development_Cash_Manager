package com.example.cashmanager.data.api

import com.example.cashmanager.data.model.Basket
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface BasketApi {
    @POST("/basket")
    fun createBasket(@Body body : RequestBody) : Call<Basket>
    @GET("/basket/all")
    fun getAllBaskets() : Call<List<Basket>>
    @PUT("/basket/{id}")
    fun updateBasket(@Path("id") id: Int, @Body body : RequestBody) : Call<Basket>
}