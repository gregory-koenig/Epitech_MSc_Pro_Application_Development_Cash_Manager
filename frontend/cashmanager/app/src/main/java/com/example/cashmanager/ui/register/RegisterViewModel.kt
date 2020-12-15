package com.example.cashmanager.ui.register

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cashmanager.R
import com.example.cashmanager.data.api.ApiService
import com.example.cashmanager.data.api.AuthenticationInterceptor
import com.example.cashmanager.data.model.User
import com.example.cashmanager.ui.login.LoggedInUserView
import com.example.cashmanager.ui.login.LoginResult
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel() : ViewModel() {

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val apiService: ApiService = ApiService()

    fun register(email: String, username: String, password: String) {
        apiService.user.register(buildBody(email, username, password))
            .enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("Error on RESPONSE = ", t.localizedMessage)
                    _loginResult.value = LoginResult(error = R.string.register_failed)
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (!response.isSuccessful) {
                        _loginResult.value = LoginResult(error = R.string.register_failed)
                    } else {
                        login(username, password)
                    }

                }
            })
    }

    fun registerDataChanged(email: String, username: String, password: String) {
        if (!isUserNameValid(username)) {
            _registerForm.value = RegisterFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else if (!isEmailValid(email)) {
            _registerForm.value = RegisterFormState(emailError = R.string.invalid_email)
        } else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.length > 6
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 6
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotBlank()
    }

    private fun buildBody(email: String, username: String, password: String): RequestBody {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        return JSONObject().put("email", email).put("username", username).put("password", password)
            .toString()
            .toRequestBody(mediaType)
    }

    private fun buildBodyLogin(username: String, password: String): RequestBody {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        return JSONObject().put("username", username).put("password", password).toString()
            .toRequestBody(mediaType)
    }

    private fun login(username: String, password: String) {
        apiService.user.login(buildBodyLogin(username, password))
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
}