package com.example.cashmanager.data.api

import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import okhttp3.ResponseBody
import org.json.JSONObject

class ApiResponse(data: LinkedTreeMap<String, String>) {
    var code: String? = "404"
    var data: String ? = null

    init {
        this.data = data["message"]
        this.code = data["statusCode"]
    }
}