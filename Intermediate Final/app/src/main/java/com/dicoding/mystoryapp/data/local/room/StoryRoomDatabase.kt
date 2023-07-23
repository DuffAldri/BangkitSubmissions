package com.dicoding.mystoryapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.mystoryapp.data.local.entity.RemoteKeys
import com.dicoding.mystoryapp.data.local.entity.StoryEntity

@Database(
	entities = [StoryEntity::class, RemoteKeys::class],
	version = 2,
	exportSchema = true,
)
abstract class StoryRoomDatabase : RoomDatabase() {
	
	abstract fun storyDao(): StoryDao
	abstract fun remoteKeysDao(): RemoteKeysDao
	
	companion object {
		@Volatile
		private var INSTANCE: StoryRoomDatabase? = null
		
		@JvmStatic
		fun getInstance(context: Context): StoryRoomDatabase {
			if(INSTANCE == null) {
				synchronized(StoryRoomDatabase::class.java) {
					INSTANCE = Room.databaseBuilder(
						context.applicationContext,
						StoryRoomDatabase::class.java, "story_database"
					)
						.build()
				}
			}
			return INSTANCE as StoryRoomDatabase
		}
	}
}