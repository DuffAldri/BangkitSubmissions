package com.dicoding.mystoryapp.utils

import com.dicoding.mystoryapp.data.local.entity.StoryEntity

object DataDummy {
	
	fun generateDummyStoryEntity(): List<StoryEntity> {
		val storyList = ArrayList<StoryEntity>()
		for (i in 0..100) {
			val story = StoryEntity(
				"storyid-$i",
				"Sender$i",
				"Lorem ipsum dolor sit amet tapi dikirim no $i",
				"https://story-api.dicoding.dev/images/stories/photos-1684194713706_Q-yCM44R.jpg",
				"2023-05-15T23:51:53.707Z",
				0.5f + i.toFloat(),
				0.75f
			)
			storyList.add(story)
		}
		return storyList
	}
}