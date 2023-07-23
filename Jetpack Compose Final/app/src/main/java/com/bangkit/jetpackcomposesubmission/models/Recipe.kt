package com.bangkit.jetpackcomposesubmission.models

data class Recipe(
	val id: String,
	val title: String,
	val photoUrl: String,
	val tags: List<String>,
	val nutrition: Map<String, Float>,
	val ingredients: List<String>,
	val directions: List<String>,
	val description: String,
)


