package com.dicoding.mystoryapp.data.local.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Entity(tableName = "stories")
@Parcelize
data class StoryEntity(
	@PrimaryKey
	@ColumnInfo(name = "id")
	val id: String,
	
	@ColumnInfo(name = "name")
	val name: String,
	
	@ColumnInfo(name = "description")
	val description: String,
	
	@ColumnInfo(name = "photoUrl")
	val photoUrl: String,
	
	@ColumnInfo(name = "createdAt")
	val createdAt: String,
	
	@ColumnInfo(name = "lat")
	val lat: Float = 0f,
	
	@ColumnInfo(name = "lon")
	val lon: Float = 0f,
	
) : Parcelable
