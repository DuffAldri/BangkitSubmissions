package com.dicoding.mystoryapp.ui.detailstory

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.dicoding.mystoryapp.databinding.ActivityStoryDetailsBinding

class StoryDetailsActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityStoryDetailsBinding
	private val storyDetailsViewModel by viewModels<StoryDetailsViewModel>()
	
	companion object {
		const val STORY_NAME = "story_name"
		const val STORY_ID = "story_id"
		const val STORY_PHOTO = "story_photo"
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityStoryDetailsBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		val id = intent.getStringExtra(STORY_ID)
		val name = intent.getStringExtra(STORY_NAME)
		val photoUrl = intent.getStringExtra(STORY_PHOTO)
		
		binding.tvDetailName.text = name
		
		val requestOptions = RequestOptions()
			.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
		
		Glide.with(this)
			.load(photoUrl)
			.apply(requestOptions)
			.into(binding.ivDetailPhoto)
		
		id?.let { storyDetailsViewModel.getDetails(it) }
		
		storyDetailsViewModel.storyDetailsResponse.observe(this) {
			
			if(it != null) {
				binding.tvDetailDescription.text = it.story.description
			}
		}
		
		storyDetailsViewModel.isLoading.observe(this) {
			showLoading(it)
		}
	}
	
	private fun showLoading(isLoading: Boolean) {
		binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
	}
}