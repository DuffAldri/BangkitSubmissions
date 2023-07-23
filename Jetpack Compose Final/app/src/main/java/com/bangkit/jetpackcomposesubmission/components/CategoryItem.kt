package com.bangkit.jetpackcomposesubmission.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.jetpackcomposesubmission.ui.theme.JetpackComposeTheme

@Composable
fun CategoryItem(
	title: String,
	photoUrl: String,
	modifier: Modifier = Modifier,
) {
	Box(
		contentAlignment = Alignment.Center,
		modifier = modifier
			.width(160.dp)
			.height(80.dp)
			.clip(RoundedCornerShape(8.dp))
	) {
//		AsyncImage(
//			model = photoUrl,
//			contentDescription = null,
//			contentScale = ContentScale.Crop,
//		)
		Box(
			modifier = Modifier
				.fillMaxSize()
				.background(color = Color.Black.copy(alpha = 0.2f))
		)
		Text(
			text = title,
			style = MaterialTheme.typography.headlineSmall.copy(
				fontWeight = FontWeight.Bold
			),
			color = Color.White,
			modifier = modifier
		)
	}
}

@Preview
@Composable
fun CategoryItemPreview() {
	JetpackComposeTheme() {
		CategoryItem("Indian", "https://assets.epicurious.com/photos/63b5b03305dd27a0d03a18a6/1:1/w_1920,c_limit/Jackfruit%20curry-RECIPE.jpg")
	}
}