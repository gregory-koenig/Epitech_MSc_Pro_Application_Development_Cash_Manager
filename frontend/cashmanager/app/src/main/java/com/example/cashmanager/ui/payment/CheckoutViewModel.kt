package com.example.cashmanager.ui.payment

import androidx.lifecycle.ViewModel
import com.example.cashmanager.data.api.ApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class CheckoutViewModel : ViewModel() {

    private val apiService: ApiService = ApiService()

    fun pay(amount: Int, token: String, description: String) {
//        apiService.payment.chargePayment(buildBody(amount, token, description))
    }

//    private fun buildBody(amount: Int, token: String, description: String): RequestBody {
//        val mediaType = "application/json; charset=utf-8".toMediaType()
//        return JSONObject()
//                .put("amount", 32)
//                .put("stripeToken", "tok_amex")
//                .put("description", "hello")
//                .toString()
//                .toRequestBody(mediaType)
//    }

    fun paymentIntent(paymentMethodeType: String, amount: Int) {
        apiService.payment.createPaymentIntent(buildBody(paymentMethodeType, amount))
        System.out.println("XXXXXXX -> paymentIntentVM" + amount)
    }

    private fun buildBody(paymentMethodeType: String, amount: Int): RequestBody {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        return JSONObject()
                .put("paymentMethodeType", paymentMethodeType)
                .put("amount", amount)
                .toString()
                .toRequestBody(mediaType)
    }
}