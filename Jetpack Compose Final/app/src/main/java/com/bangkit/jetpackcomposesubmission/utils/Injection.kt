package com.bangkit.jetpackcomposesubmission.utils

import com.bangkit.jetpackcomposesubmission.models.RecipeRepository

object Injection {
	
	fun provideRecipeRepository(): RecipeRepository {
		return RecipeRepository.getInstance()
	}
}