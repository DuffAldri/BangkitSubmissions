package com.bangkit.jetpackcomposesubmission

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bangkit.jetpackcomposesubmission.components.BottomBar
import com.bangkit.jetpackcomposesubmission.ui.navigation.Screen
import com.bangkit.jetpackcomposesubmission.ui.screen.add.AddScreen
import com.bangkit.jetpackcomposesubmission.ui.screen.recipedetails.RecipeDetailsScreen
import com.bangkit.jetpackcomposesubmission.ui.screen.home.HomeScreen
import com.bangkit.vegalicious.ui.screen.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetpackComposeApp(
	modifier: Modifier = Modifier,
	navController: NavHostController = rememberNavController(),
) {
	Scaffold(
		bottomBar = { BottomBar(navController = navController) },
	) { it ->
		NavHost(
			navController = navController,
			startDestination = Screen.Home.route,
			modifier = Modifier.padding(it),
		) {
			composable(Screen.Home.route) {
				HomeScreen(
					navigateToDetail = {
						navController.navigate(Screen.DetailRecipe.createRoute(it))
					},
				)
			}
			composable(Screen.Add.route) {
				AddScreen(
					navigateToHome = {
						navController.navigate(Screen.Home.route) {
							popUpTo(Screen.Add.route) { inclusive = true }
						}
					},
				)
			}
			composable(
				route = Screen.DetailRecipe.route,
				arguments = listOf(navArgument("recipeId") {
					type = NavType.StringType
				}),
			) {
				val id = it.arguments?.getString("recipeId") ?: "null"
				RecipeDetailsScreen(
					recipeId = id,
				)
			}
			composable(
				route = Screen.Profile.route
			) {
				ProfileScreen()
			}
		}
	}
}