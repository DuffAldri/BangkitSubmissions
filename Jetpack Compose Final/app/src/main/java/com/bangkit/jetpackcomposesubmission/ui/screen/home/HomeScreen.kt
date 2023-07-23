package com.bangkit.jetpackcomposesubmission.ui.screen.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.jetpackcomposesubmission.ui.theme.JetpackComposeTheme
import com.bangkit.jetpackcomposesubmission.components.RecipeItem
import com.bangkit.jetpackcomposesubmission.ui.common.UiState
import com.bangkit.jetpackcomposesubmission.utils.Injection
import com.bangkit.jetpackcomposesubmission.utils.ViewModelFactory
import com.bangkit.vegalicious.components.OutlinedSearchBar
import com.dicoding.jetcoffee.components.SectionText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
	viewModel: HomeViewModel = viewModel(
		factory = ViewModelFactory(
			Injection.provideRecipeRepository(),
		)
	),
	navigateToDetail: (String) -> Unit,
) {
	var input by remember { mutableStateOf("") }
	
	Scaffold(
		topBar = { AppBar(
			input = input,
			onValueChange = { newValue ->
				input = newValue
				viewModel.getRecipeWithName(newValue)
			},
		) }
	) {paddingValues ->
		
		viewModel.uiStateRecipe.collectAsState(initial = UiState.Loading).value.let { uiState ->
			when(uiState) {
				is UiState.Loading -> {
					viewModel.getAllRecipes()
				}
				is UiState.Success -> {
					LazyVerticalGrid(
						columns = GridCells.Fixed(2),
						verticalArrangement = Arrangement.spacedBy(16.dp),
						horizontalArrangement = Arrangement.spacedBy(16.dp),
						contentPadding = PaddingValues(horizontal = 16.dp),
						modifier = Modifier
							.padding(paddingValues)
					) {
						item(span = { GridItemSpan(maxCurrentLineSpan) }) {
							SectionText(title = "Recipes", modifier = Modifier.padding(top = 16.dp))
						}
						items(uiState.data) {
							RecipeItem(
								modifier = Modifier
									.width(200.dp),
								title = it.title,
								photoUrl = it.photoUrl,
								tags = it.tags,
								description = it.description,
								enableTags = false,
								onClick = { navigateToDetail(it.id) }
							)
						}
					}
				}
				is UiState.Error -> {
				
				}
			}
		}
	}
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
	input: String = "",
	onValueChange: (String) -> Unit = {},
	onSearch: () -> Unit = {}
) {
	TopAppBar(
		title = {
			Text("Recipes")
		},
		actions = {
			Row (
				modifier = Modifier.padding(start = 8.dp, end = 16.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				OutlinedSearchBar(
					value = input,
					onValueChange = onValueChange,
					onSearch = onSearch,
				)
			}
		},
		modifier = Modifier.padding(vertical = 8.dp)
	)
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
	JetpackComposeTheme() {
		// A surface container using the 'background' color from the theme
		Surface(
			modifier = Modifier.fillMaxSize(),
			color = MaterialTheme.colorScheme.background
		) {
			HomeScreen(navigateToDetail = {})
		}
	}
}