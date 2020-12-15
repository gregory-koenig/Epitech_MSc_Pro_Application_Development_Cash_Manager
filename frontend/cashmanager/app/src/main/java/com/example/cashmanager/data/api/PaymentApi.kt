package com.example.cashmanager.data.api

import com.example.cashmanager.data.model.Intent
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface PaymentApi {
    @POST("/payment_intents")
    fun createPaymentIntent(@Body payment: RequestBody) : Call<Intent>
}