package com.bangkit.jetpackcomposesubmission.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bangkit.jetpackcomposesubmission.R
import com.bangkit.jetpackcomposesubmission.ui.navigation.NavigationItem
import com.bangkit.jetpackcomposesubmission.ui.navigation.Screen

@Composable
fun BottomBar(
	modifier: Modifier = Modifier,
	navController: NavHostController,
) {
	NavigationBar(
		modifier = modifier
	) {
		val navigationItems = listOf(
			NavigationItem(
				title = stringResource(R.string.menu_home),
				icon = Icons.Default.Home,
				screen = Screen.Home,
				label = "home_page"
			),
			NavigationItem(
				title = stringResource(R.string.menu_add),
				icon = Icons.Default.Add,
				screen = Screen.Add,
				label = "add_page"
			),
			NavigationItem(
				title = stringResource(R.string.menu_profile),
				icon = Icons.Default.AccountCircle,
				screen = Screen.Profile,
				label = "about_page"
			),
		)
		
		navigationItems.map { item ->
			NavigationBarItem(
				selected = true,
				onClick = {
					navController.navigate(item.screen.route) {
						popUpTo(navController.graph.findStartDestination().id) {
							saveState = true
						}
						restoreState = true
						launchSingleTop = true
					}
				},
				label = { Text(item.title) },
				icon = {
					Icon(
						imageVector = item.icon,
						contentDescription = item.title
					)
				}
			)
		}
		
	}
}

@Preview
@Composable
fun BottomBarPreview() {

}