package com.example.cashmanager.ui.payment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cashmanager.data.api.ApiService
import com.example.cashmanager.data.model.Intent
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentViewModel : ViewModel() {
    private val apiService: ApiService = ApiService()

    val secretClient = MutableLiveData<String?>()

    fun getSecretClient(amount : Int) {
        val body = buildBody("card", amount)

        apiService.payment.createPaymentIntent(body)
            .enqueue(object: Callback<Intent> {
                override fun onFailure(call: Call<Intent>, t: Throwable) {
                    Log.e("[Payment]", "Failure: ${t.localizedMessage}")
                    Log.e("payment error = ", t.stackTrace.toString())
                    secretClient.apply { value = null }
                }

                override fun onResponse(call: Call<Intent>, response: Response<Intent>) {
                    Log.i("response = ", response.body().toString())
                    if (response.isSuccessful) {
                        Log.e("[Payment]", "Success: ${response.body()!!}")
                        secretClient.apply { value = response.body()!!.client_secret }
                    } else {
                        try {
                            val obj = JSONObject(response.errorBody()!!.string())
                            Log.e("[Payment]", "Error: $obj")
                        } catch (e: Exception) {
                            Log.e("[Payment]", "Error: could not deserialize error object")
                        }
                        secretClient.apply { value = null }
                    }
                }
            })
    }

    private fun buildBody(paymentMethodeType: String, amount: Int): RequestBody {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        return JSONObject()
            .put("paymentMethodTypes", paymentMethodeType)
            .put("amount", amount)
            .toString()
            .toRequestBody(mediaType)
    }
}



