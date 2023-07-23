package com.dicoding.mystoryapp.ui.addstory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mystoryapp.data.remote.response.UploadResponse
import com.dicoding.mystoryapp.data.remote.retrofit.ApiConfig
import com.dicoding.mystoryapp.ui.auth.register.RegisterViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryAddViewModel : ViewModel() {
	
	private val _upload = MutableLiveData<UploadResponse>()
	val upload: LiveData<UploadResponse> = _upload
	
	private val _isLoading = MutableLiveData<Boolean>()
	val isLoading: LiveData<Boolean> = _isLoading
	
	private val _errorMessage = MutableLiveData<String?>()
	val errorMessage: MutableLiveData<String?> = _errorMessage
	
	fun getUploadResponse(imageMultipart: MultipartBody.Part, description: RequestBody, lat: RequestBody? = null, lon: RequestBody? = null) {
		
		_isLoading.value = true
		val client =
			if(lat != null && lon != null) {
				ApiConfig.getApiService().uploadImageWithLoc(imageMultipart, description, lat, lon)
			} else {
				ApiConfig.getApiService().uploadImage(imageMultipart, description)
			}
		client.enqueue(object : Callback<UploadResponse> {
			
			override fun onResponse(
				call: Call<UploadResponse>,
				response: Response<UploadResponse>
			) {
				_isLoading.value = false
				
				val responseBody = response.body()
				if(responseBody != null) {
					_upload.value = responseBody
				}
			}
			override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
				_isLoading.value = false
				Log.e(TAG, "onFailure ${t.message.toString()}")
				_errorMessage.value = RegisterViewModel.LOGIN_FAILURE			}
		})
	}
	
	fun clearErrorMessage() {
		_errorMessage.value = null
	}
	
	companion object {
		const val TAG = "StoryAddViewModel"
	}
}