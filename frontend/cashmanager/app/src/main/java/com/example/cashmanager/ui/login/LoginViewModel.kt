package com.example.cashmanager.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.cashmanager.R
import com.example.cashmanager.data.api.ApiService
import com.example.cashmanager.data.api.AuthenticationInterceptor
import com.example.cashmanager.data.model.User
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel() : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val apiService: ApiService = ApiService()

    fun login(username: String, password: String) {
        apiService.user.login(buildBody(username, password))
            .enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("Error on RESPONSE = ", t.localizedMessage)
                    _loginResult.value = LoginResult(error = R.string.login_failed)
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {

                    if (!response.isSuccessful) {
                        _loginResult.value = LoginResult(error = R.string.login_invalid)
                    } else {
                        AuthenticationInterceptor.token = response.headers().get("Authorization").toString()
                        _loginResult.value =
                            LoginResult(success = LoggedInUserView(displayName = username))
                    }

                }
            })
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.isNotBlank()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 6
    }

    private fun buildBody(username: String, password: String): RequestBody {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        return JSONObject().put("username", username).put("password", password).toString()
            .toRequestBody(mediaType)
    }
}