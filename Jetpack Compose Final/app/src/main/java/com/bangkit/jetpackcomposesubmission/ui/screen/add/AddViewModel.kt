package com.bangkit.jetpackcomposesubmission.ui.screen.add

import androidx.lifecycle.ViewModel
import com.bangkit.jetpackcomposesubmission.models.Recipe
import com.bangkit.jetpackcomposesubmission.models.RecipeRepository
import com.bangkit.jetpackcomposesubmission.utils.splitToList
import java.util.UUID

class AddViewModel(
	private val recipeRepository: RecipeRepository,
	) : ViewModel() {
	
	fun saveRecipe(
		photoUrl: String,
		title: String,
		tagsInString: String,
		description: String,
		calories: String,
		fat: String,
		protein: String,
		sodium: String,
		ingredientsInString: String,
		directionsInString: String
	) {
		val recipe = Recipe(
			photoUrl = photoUrl,
			id = UUID.randomUUID().toString(),
			title = title,
			tags = splitToList(tagsInString),
			description = description,
			nutrition = mapOf(
				"fat" to fat.toFloat(),
				"calories" to calories.toFloat(),
				"protein" to protein.toFloat(),
				"sodium" to sodium.toFloat()
			),
			ingredients = splitToList(ingredientsInString),
			directions = splitToList(directionsInString)
		)
		
		recipeRepository.addRecipe(recipe)
	}
}