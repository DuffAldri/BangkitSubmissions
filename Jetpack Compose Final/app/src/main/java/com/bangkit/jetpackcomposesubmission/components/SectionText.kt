package com.dicoding.jetcoffee.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SectionText(
	title: String,
	modifier: Modifier = Modifier
		.padding(horizontal = 16.dp, vertical = 8.dp),
) {
	Text(
		text = title,
		style = MaterialTheme.typography.titleLarge.copy(
			fontWeight = FontWeight.Bold
		),
		color = MaterialTheme.colorScheme.primary,
		modifier = modifier,
	)
}