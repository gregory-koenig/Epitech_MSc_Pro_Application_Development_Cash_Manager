package com.example.cashmanager.data.api

import com.example.cashmanager.data.model.Basket
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BasketApi {
    @POST("/basket")
    fun createBasket(@Body body : RequestBody) : Call<Basket>
}