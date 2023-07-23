package com.dicoding.mystoryapp.data

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.mystoryapp.data.local.entity.StoryEntity
import com.dicoding.mystoryapp.data.local.room.StoryRoomDatabase
import com.dicoding.mystoryapp.data.remote.retrofit.ApiService

class StoryRepository (
	val apiService: ApiService,
	private val database: StoryRoomDatabase
) {
	
	@OptIn(ExperimentalPagingApi::class)
	fun getAllStories(): LiveData<PagingData<StoryEntity>> {
		
		return Pager(
			config = PagingConfig(
				pageSize = 10
			),
			remoteMediator = StoryRemoteMediator(database, apiService),
			pagingSourceFactory = {
				database.storyDao().getAllStories()
			}
		).liveData
	}
	
	companion object {
	}
}