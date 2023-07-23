package com.bangkit.jetpackcomposesubmission.models

import com.bangkit.vegalicious.models.FakeRecipes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RecipeRepository {
	
	private val recipes = mutableListOf<Recipe>()
	init {
		if(recipes.isEmpty()) {
			FakeRecipes.dummyRecipes.forEach {
				recipes.add(it)
			}
		}
	}
	
	fun getAllRecipes(): Flow<List<Recipe>> {
		return flowOf(recipes)
	}
	
	fun getRecipeById(id: String): Recipe {
		return recipes.find { it.id == id } as Recipe // This could be null. Jangan lupa cari logika lain.
	}
	
	fun getRecipeByName(name: String): Flow<List<Recipe>> {
		val filtered = recipes.filter { it.title.contains(name) }
		return flowOf(filtered)
	}
	
	fun addRecipe(recipe: Recipe) {
		FakeRecipes.dummyRecipes.add(recipe)
		recipes.add(recipe)
	}
	
	companion object {
		@Volatile
		private var instance: RecipeRepository? = null
		
		fun getInstance(): RecipeRepository =
			instance ?: synchronized(this) {
				RecipeRepository().apply {
					instance = this
				}
			}
	}
}