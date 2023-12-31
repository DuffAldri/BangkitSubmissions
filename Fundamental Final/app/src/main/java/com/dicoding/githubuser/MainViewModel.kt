package com.dicoding.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<User>?>()
    val listUser: MutableLiveData<List<User>?> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        findUser("A")
    }

    internal fun findUser(input: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(input)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false

                val responseBody = response.body()
                if (responseBody != null) {
                    _listUser.value = responseBody.items
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure ${t.message.toString()}")
            }
        })
    }
}