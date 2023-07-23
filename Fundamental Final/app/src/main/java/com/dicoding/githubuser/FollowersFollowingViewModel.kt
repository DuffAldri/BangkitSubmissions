package com.dicoding.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersFollowingViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<FollowersFollowingResponseItem>?>()
    val listUser: MutableLiveData<List<FollowersFollowingResponseItem>?> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FollowViewModel"
    }

    internal fun findUser(input: String, index: Int) {
        _isLoading.value = true
        val client = if(index == 0) {
            ApiConfig.getApiService().getUserFollowers(input)
        } else {
            ApiConfig.getApiService().getUserFollowing(input)
        }
        client.enqueue(object : Callback<List<FollowersFollowingResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowersFollowingResponseItem>>,
                response: Response<List<FollowersFollowingResponseItem>>
            ) {
                _isLoading.value = false

                val responseBody = response.body()
                if (responseBody != null) {
                    _listUser.value = responseBody
                }
            }

            override fun onFailure(call: Call<List<FollowersFollowingResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure ${t.message.toString()}")
            }
        })
    }

}