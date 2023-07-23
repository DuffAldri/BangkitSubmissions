package com.dicoding.mystoryapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.mystoryapp.data.StoryRepository
import com.dicoding.mystoryapp.data.local.entity.StoryEntity

class MainViewModel(private val storyRepository: StoryRepository) : ViewModel() {
	
	fun getAllStories(): LiveData<PagingData<StoryEntity>> =
		storyRepository.getAllStories().cachedIn(viewModelScope)
	
}