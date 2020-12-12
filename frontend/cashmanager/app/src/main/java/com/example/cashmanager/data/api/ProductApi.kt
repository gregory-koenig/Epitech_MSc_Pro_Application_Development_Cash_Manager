package com.example.cashmanager.data.api

import com.example.cashmanager.data.model.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {
    @GET("/product/{id}")
    fun getProductById(@Path("id") id: Int): Call<Product>
}