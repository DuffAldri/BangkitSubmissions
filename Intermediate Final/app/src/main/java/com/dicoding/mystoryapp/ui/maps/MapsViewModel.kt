package com.dicoding.mystoryapp.ui.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mystoryapp.data.StoryRepository
import com.dicoding.mystoryapp.data.remote.response.StoriesResponse
import com.dicoding.mystoryapp.data.remote.response.StoryItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel(_storyRepository: StoryRepository) : ViewModel() {
	
	private val _listStory = MutableLiveData<List<StoryItem>>()
	val listStory: LiveData<List<StoryItem>> = _listStory
	
	private val _isLoading = MutableLiveData<Boolean>()
	val isLoading: LiveData<Boolean> = _isLoading
	
	private val storyRepository = _storyRepository
	
	init {
		getAllStory()
	}
	
	private fun getAllStory() {
		_isLoading.value = true
		val client = storyRepository.apiService.getStoriesWithSize(size)
		client.enqueue(object : Callback<StoriesResponse> {
			override fun onResponse(
				call: Call<StoriesResponse>,
				response: Response<StoriesResponse>
			) {
				_isLoading.value = false
				
				val responseBody = response.body()
				if(responseBody != null)
					_listStory.value = responseBody.listStory
			}
			
			override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
				_isLoading.value = false
				Log.e(TAG, "onFailure ${t.message.toString()}")
			}
			
		})
	}
	
	companion object {
		private const val size = 100
		private const val TAG = "MapsViewModel"
	}
}