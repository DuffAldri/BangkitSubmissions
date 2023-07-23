package com.dicoding.mystoryapp.di

import android.content.Context
import com.dicoding.mystoryapp.data.StoryRepository
import com.dicoding.mystoryapp.data.local.room.StoryRoomDatabase
import com.dicoding.mystoryapp.data.remote.retrofit.ApiConfig

object Injection {
	fun provideRepository(context: Context): StoryRepository {
		val apiService = ApiConfig.getApiService()
		val database = StoryRoomDatabase.getInstance(context)
		return StoryRepository(apiService, database)
	}
}