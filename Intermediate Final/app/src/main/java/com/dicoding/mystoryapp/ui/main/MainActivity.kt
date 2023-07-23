package com.dicoding.mystoryapp.ui.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mystoryapp.R
import com.dicoding.mystoryapp.adapter.LoadingStateAdapter
import com.dicoding.mystoryapp.adapter.StoryAdapter
import com.dicoding.mystoryapp.data.remote.retrofit.ApiConfig
import com.dicoding.mystoryapp.databinding.ActivityMainBinding
import com.dicoding.mystoryapp.ui.addstory.StoryAddActivity
import com.dicoding.mystoryapp.ui.auth.login.LoginActivity
import com.dicoding.mystoryapp.ui.maps.MapsActivity
import com.dicoding.mystoryapp.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {
	
	private var binding: ActivityMainBinding? = null
	private lateinit var sharedPreferences: SharedPreferences
	private val mainViewModel: MainViewModel by viewModels {
		ViewModelFactory(this)
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding?.root)
		
		sharedPreferences = getSharedPreferences("mystoryapp-data", MODE_PRIVATE)
		
		val auth = sharedPreferences.getString("authToken", null)
		if(auth != null) {
			ApiConfig.setAuth(auth)
		}
		
		val layoutManager = LinearLayoutManager(this)
		binding?.rvStory?.layoutManager = layoutManager
		
		getData().also { Log.d(TAG, "getData()") }
		
		binding?.addStoryFAB?.setOnClickListener {
			val intent = Intent(this, StoryAddActivity::class.java)
			startActivity(intent)
		}
		
	}
	
	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		val inflater = menuInflater
		inflater.inflate(R.menu.main_menu, menu)
		
		return true
	}
	
	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when(item.itemId) {
			R.id.action_logout -> {
				sharedPreferences.edit().clear().apply()
				ApiConfig.clearAuth()
				val intent = Intent(this, LoginActivity::class.java)
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
				startActivity(intent)
				return true
			}
			R.id.action_maps -> {
				val intent = Intent(this, MapsActivity::class.java)
				startActivity(intent)
				return true
			}
		}
		
		return super.onOptionsItemSelected(item)
	}
	
	override fun onDestroy() {
		super.onDestroy()
		binding = null
	}
	
	private fun getData() {
		val adapter = StoryAdapter().also { Log.d(TAG, "Memanggil adapter, $it")}
		binding?.rvStory?.adapter = adapter.withLoadStateFooter(
			footer = LoadingStateAdapter {
				adapter.retry()
			}
		)
		
		mainViewModel.getAllStories().observe(this) {
			adapter.submitData(lifecycle, it).also { Log.d(TAG, "submitData") }
		}
		
	}
	
	companion object {
		const val TAG = "MainActivity"
	}
}