package com.dicoding.mystoryapp.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.dicoding.mystoryapp.data.local.entity.StoryEntity
import com.dicoding.mystoryapp.databinding.ItemStoryRowBinding
import com.dicoding.mystoryapp.ui.detailstory.StoryDetailsActivity
import com.dicoding.mystoryapp.utils.toDescriptionPreview

class StoryAdapter :
	PagingDataAdapter<StoryEntity, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK) {
	
	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): StoryAdapter.StoryViewHolder {
		Log.d(TAG, "Memulai onCreateViewHolder()")
		val binding =
			ItemStoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return StoryViewHolder(binding)
	}
	
	override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
		Log.d(TAG, "Memulai onBindViewHolder()")
		val data = getItem(position)
		if(data != null) {
			holder.bind(data)
		} else Log.e(TAG, "Null data")
	}
	
	inner class StoryViewHolder(var binding: ItemStoryRowBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(data: StoryEntity) {
			Log.d(TAG, "Memulai bind()")
			binding.tvItemName.text = data.name
			
			val requestOptions = RequestOptions()
				.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
			
			Glide.with(itemView.context)
				.load(data.photoUrl)
				.apply(requestOptions)
				.into(binding.ivItemPhoto)
			
			binding.tvItemDescription.text = toDescriptionPreview(data.description)
			
			binding.storyCard.setOnClickListener {
				val storyDetails = Intent(itemView.context, StoryDetailsActivity::class.java)
				storyDetails.putExtra(StoryDetailsActivity.STORY_ID, data.id)
				storyDetails.putExtra(StoryDetailsActivity.STORY_NAME, data.name)
				storyDetails.putExtra(StoryDetailsActivity.STORY_PHOTO, data.photoUrl)
				
				var imagePair = androidx.core.util.Pair(binding.ivItemPhoto as View, "image")
				val namePair = androidx.core.util.Pair(binding.tvItemName as View, "name")
				
				val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
					itemView.context as Activity,
					imagePair,
					namePair
				)
				itemView.context.startActivity(storyDetails, options.toBundle())
			}
		}
	}
	
	companion object {
		const val TAG = "StoryAdapter"
		
		val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryEntity>() {
			override fun areItemsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
				return oldItem == newItem
			}
			
			override fun areContentsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
				return oldItem.id == newItem.id
			}
		}
	}
}