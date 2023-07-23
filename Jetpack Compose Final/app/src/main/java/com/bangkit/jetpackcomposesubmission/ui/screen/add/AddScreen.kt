package com.bangkit.jetpackcomposesubmission.ui.screen.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.jetpackcomposesubmission.utils.Injection
import com.bangkit.jetpackcomposesubmission.utils.ViewModelFactory

@Composable
fun AddScreen(
	navigateToHome: () -> Unit,
	addViewModel: AddViewModel = viewModel(
		factory = ViewModelFactory(
			Injection.provideRecipeRepository(),
		)
	)
) {
	var title by remember { mutableStateOf("") }
	var photoUrl by remember { mutableStateOf("") }
	var tags by remember { mutableStateOf("") }
	var description by remember { mutableStateOf("") }
	var calories by remember { mutableStateOf("") }
	var fat by remember { mutableStateOf("") }
	var protein by remember { mutableStateOf("") }
	var sodium by remember { mutableStateOf("") }
	var ingredients by remember { mutableStateOf("") }
	var directions by remember { mutableStateOf("") }
	
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(16.dp),
		modifier = Modifier
			.verticalScroll(rememberScrollState())
			.padding(horizontal = 16.dp, vertical = 16.dp)
	) {
		AddTextField(
			input = title,
			onValueChange = { newValue -> title = newValue },
			onEmpty = { title = "" },
			label = "Recipe name",
			placeholder = "Recipe name"
		)
		AddTextField(
			input = photoUrl,
			onValueChange = { newValue -> photoUrl = newValue },
			onEmpty = { photoUrl = "" },
			label = "Photo URL",
			placeholder = "example.com/image/recipe.jpg"
		)
		AddTextField(
			input = tags,
			onValueChange = { newValue -> tags = newValue },
			onEmpty = { tags = "" },
			label = "Tags (Use ; for separating each items)",
			placeholder = "Tag 1;Tag 2;Tag 3;Tag 4"
		)
		AddTextField(
			input = description,
			onValueChange = { newValue -> description = newValue },
			onEmpty = { description = "" },
			label = "Description",
			placeholder = "Recipe description..."
		)
		AddTextField(
			input = calories,
			onValueChange = { newValue -> calories = newValue },
			onEmpty = { calories = "" },
			label = "Calories",
			placeholder = "3.14"
		)
		AddTextField(
			input = fat,
			onValueChange = { newValue -> fat = newValue },
			onEmpty = { fat = "" },
			label = "Fat",
			placeholder = "3.14"
		)
		AddTextField(
			input = protein,
			onValueChange = { newValue -> protein = newValue },
			onEmpty = { protein = "" },
			label = "Protein",
			placeholder = "3.14"
		)
		AddTextField(
			input = sodium,
			onValueChange = { newValue -> sodium = newValue },
			onEmpty = { sodium = "" },
			label = "Sodium",
			placeholder = "3140.5"
		)
		AddTextField(
			input = ingredients,
			onValueChange = { newValue -> ingredients = newValue },
			onEmpty = { ingredients = "" },
			label = "Ingredients (Use ; for separating each items)",
			placeholder = "10 Item 1;5 Item 2;3 Item 3"
		)
		AddTextField(
			input = directions,
			onValueChange = { newValue -> directions = newValue },
			onEmpty = { directions = "" },
			label = "Directions (Use ; for separating each items)",
			placeholder = "10 Item 1;5 Item 2;3 Item 3"
		)
		Button(onClick = {
			addViewModel.saveRecipe(
				photoUrl = photoUrl,
				title = title,
				tagsInString = tags,
				description = description,
				calories = calories,
				fat = fat,
				protein = protein,
				sodium = sodium,
				ingredientsInString = ingredients,
				directionsInString = directions
			)
			
		}) {
			Text("Add")
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTextField(
	input: String,
	onValueChange: (String) -> Unit,
	onEmpty: () -> Unit,
	label: String,
	placeholder: String
) {
	TextField(
		value = input,
		onValueChange = onValueChange,
		trailingIcon = {
			if(input.isNotEmpty()) {
				IconButton(
					onClick = onEmpty,
				) {
					Icon(
						Icons.Default.Cancel,
						contentDescription = "Clear"
					)
				}
			}
		},
		modifier = Modifier
			.fillMaxWidth(),
		label = { Text(label) },
		placeholder = { Text(placeholder) }
	)
}