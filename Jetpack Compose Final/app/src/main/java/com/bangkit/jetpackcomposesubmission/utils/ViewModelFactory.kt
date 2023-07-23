package com.bangkit.jetpackcomposesubmission.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.jetpackcomposesubmission.models.RecipeRepository
import com.bangkit.jetpackcomposesubmission.ui.screen.add.AddViewModel
import com.bangkit.jetpackcomposesubmission.ui.screen.recipedetails.RecipeDetailsViewModel
import com.bangkit.jetpackcomposesubmission.ui.screen.home.HomeViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory() :
	ViewModelProvider.NewInstanceFactory() {

	private lateinit var recipeRepository: RecipeRepository
	
	constructor(recipeRepository: RecipeRepository) : this() {
		this.recipeRepository = recipeRepository
	}
	
	@Suppress("UNCHECKED_CAST")
	override fun <T: ViewModel> create(modelClass: Class<T>): T {
		if(modelClass.isAssignableFrom(HomeViewModel::class.java))
			return HomeViewModel(recipeRepository) as T
		if(modelClass.isAssignableFrom(AddViewModel::class.java))
			return AddViewModel(recipeRepository) as T
		else if(modelClass.isAssignableFrom(RecipeDetailsViewModel::class.java))
			return RecipeDetailsViewModel(recipeRepository) as T
		throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
	}
}