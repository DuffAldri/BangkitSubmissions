package com.bangkit.jetpackcomposesubmission.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bangkit.jetpackcomposesubmission.ui.theme.JetpackComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeItem(
	title: String,
	photoUrl: String,
	tags: List<String>,
	description: String,
	modifier: Modifier = Modifier,
	enableTags: Boolean,
	onClick: () -> Unit = {}
) {
	ElevatedCard(
		modifier = modifier.height((if(enableTags)220 else 200).dp),
		colors = CardDefaults.elevatedCardColors(
			containerColor = MaterialTheme.colorScheme.background
		),
		onClick = onClick
	) {
		AsyncImage(
			model = photoUrl,
			contentDescription = null,
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.height(120.dp)
		)
		
		Column(
			horizontalAlignment = Alignment.Start
		) {
			Text(
				text = title,
				style = MaterialTheme.typography.titleSmall.copy(
					fontWeight = FontWeight.ExtraBold
				),
				modifier = Modifier
					.padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 4.dp)
			)
			if(enableTags) {
				LazyRow(
					horizontalArrangement = Arrangement.spacedBy(2.dp),
					contentPadding = PaddingValues(horizontal = 8.dp),
				) {
					items(tags) {
						Card(
							colors = CardDefaults.cardColors(
								containerColor = MaterialTheme.colorScheme.surfaceVariant
							),
						) {
							Text(
								modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
								text = it,
								style = MaterialTheme.typography.labelSmall
							)
							
						}
					}
				}
			}
			Text(
				text = description,
				style = MaterialTheme.typography.bodySmall,
				maxLines = 2,
				overflow = TextOverflow.Ellipsis,
				modifier = modifier
					.padding(top = 4.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
			)
		}
		
	}
}

@Preview
@Composable
fun RecipeItemTruePreview() {
	JetpackComposeTheme() {
		RecipeItem(
			"Jackfruit Curry",
			"https://assets.epicurious.com/photos/63b5b03305dd27a0d03a18a6/1:1/w_1920,c_limit/Jackfruit%20curry-RECIPE.jpg",
			listOf(
				"Jackfruit",
				"Curry",
				"Indian",
				"Lunch",
			),
			"This recipe was excerpted from 'Chetna's Healthy Indian: Vegetarian' by Chetna Makan.",
			enableTags = true
		)
	}
}

@Preview
@Composable
fun RecipeItemFalsePreview() {
	JetpackComposeTheme {
		RecipeItem(
			"Jackfruit Curry",
			"https://assets.epicurious.com/photos/63b5b03305dd27a0d03a18a6/1:1/w_1920,c_limit/Jackfruit%20curry-RECIPE.jpg",
			listOf(
				"Jackfruit",
				"Curry",
				"Indian",
				"Lunch",
			),
			"This recipe was excerpted from 'Chetna's Healthy Indian: Vegetarian' by Chetna Makan.",
			enableTags = false
		)
	}
}