package com.bangkit.jetpackcomposesubmission.ui.screen.recipedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.jetpackcomposesubmission.models.RecipeRepository
import com.bangkit.jetpackcomposesubmission.ui.common.UiState
import com.bangkit.jetpackcomposesubmission.models.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {
	private val _uiState: MutableStateFlow<UiState<Recipe>> = MutableStateFlow(UiState.Loading)
	
	val uiState: StateFlow<UiState<Recipe>>
		get() = _uiState
	
	fun getRecipeById(recipeId: String) {
		viewModelScope.launch {
			_uiState.value = UiState.Loading
			_uiState.value = UiState.Success(recipeRepository.getRecipeById(recipeId)) // Hasil bisa saja null kalau ada error. Jangan lupa cari logikanya.
		}
	}
}