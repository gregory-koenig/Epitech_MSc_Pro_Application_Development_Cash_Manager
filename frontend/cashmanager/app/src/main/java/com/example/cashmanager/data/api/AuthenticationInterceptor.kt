package com.example.cashmanager.data.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor() : Interceptor {

    companion object {
        var token = ""
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }
}