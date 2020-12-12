package com.example.cashmanager.data.api

import com.example.cashmanager.data.model.Intent
import com.example.cashmanager.data.model.Payment
import com.example.cashmanager.data.model.User
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface PaymentApi {

    @Headers("Content-Type: application/json")
    @POST("/charge")
    fun chargePayment(@Body payment: RequestBody): Call<Payment>

    @POST("/paymentIntent")
    fun createPaymentIntent(@Body payment: RequestBody) : Call<Intent>
}