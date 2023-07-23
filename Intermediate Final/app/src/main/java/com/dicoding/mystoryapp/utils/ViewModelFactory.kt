package com.dicoding.mystoryapp.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.mystoryapp.di.Injection
import com.dicoding.mystoryapp.ui.main.MainViewModel
import com.dicoding.mystoryapp.ui.maps.MapsViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
			@Suppress("UNCHECKED_CAST")
			return MainViewModel(Injection.provideRepository(context)) as T
		} else if(modelClass.isAssignableFrom(MapsViewModel::class.java)) {
			@Suppress("UNCHECKED_CAST")
			return MapsViewModel(Injection.provideRepository(context)) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}