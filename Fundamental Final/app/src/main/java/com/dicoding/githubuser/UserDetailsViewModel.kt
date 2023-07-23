package com.dicoding.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailsViewModel : ViewModel() {

    private val _userDetailResponse = MutableLiveData<UserDetailsResponse?>()
    val userDetailsResponse: LiveData<UserDetailsResponse?> = _userDetailResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "UserDetailsViewModel"
    }

    fun getDetails(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetails(login)
        client.enqueue(object : Callback<UserDetailsResponse> {
            override fun onResponse(
                call: Call<UserDetailsResponse>,
                response: Response<UserDetailsResponse>
            ) {
                _isLoading.value = false

                val responseBody = response.body()
                _userDetailResponse.value = responseBody
            }

            override fun onFailure(call: Call<UserDetailsResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure ${t.message.toString()}")
            }
        })
    }
}