package com.bangkit.jetpackcomposesubmission.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.jetpackcomposesubmission.models.RecipeRepository
import com.bangkit.jetpackcomposesubmission.ui.common.UiState
import com.bangkit.jetpackcomposesubmission.models.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
	private val recipeRepository: RecipeRepository,
) : ViewModel() {
	
	private val _uiStateRecipe: MutableStateFlow<UiState<List<Recipe>>> = MutableStateFlow(UiState.Loading)
	
	val uiStateRecipe: StateFlow<UiState<List<Recipe>>>
		get() = _uiStateRecipe
	
	fun getAllRecipes() {
		viewModelScope.launch {
			recipeRepository.getAllRecipes()
				.catch {
					_uiStateRecipe.value = UiState.Error(it.message.toString())
				}
				.collect {
					_uiStateRecipe.value = UiState.Success(it)
				}
		}
	}
	
	fun getRecipeWithName(query: String) {
		viewModelScope.launch {
			recipeRepository.getRecipeByName(query)
				.catch {
					_uiStateRecipe.value = UiState.Error(it.message.toString())
				}
				.collect {
					_uiStateRecipe.value = UiState.Success(it)
				}
		}
	}
}