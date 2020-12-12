package com.example.cashmanager.data.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    var user: UserApi
    var payment: PaymentApi
    var product : ProductApi
    var basket : BasketApi

    init {
        val host = "192.168.0.20"
        val port = 8080
        val gson = GsonBuilder()
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .setPrettyPrinting()
            .create()
        val retrofit = Retrofit.Builder()
            .client(httpClient())
            .baseUrl("http://$host:$port")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        user = retrofit.create(UserApi::class.java)
        product = retrofit.create(ProductApi::class.java)
        basket = retrofit.create(BasketApi::class.java)
        payment = retrofit.create(PaymentApi::class.java)
    }

    private fun httpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor())
            .build()
    }
}