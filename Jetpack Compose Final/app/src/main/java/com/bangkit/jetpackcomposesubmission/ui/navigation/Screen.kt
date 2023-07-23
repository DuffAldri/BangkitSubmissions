package com.bangkit.jetpackcomposesubmission.ui.navigation

sealed class Screen(val route: String) {
	
	object Home : Screen("home")
	object Add : Screen("add")
	object Profile : Screen("profile")
	object DetailRecipe : Screen("home/{recipeId}") {
		fun createRoute(recipeId: String) = "home/$recipeId"
	}
}
