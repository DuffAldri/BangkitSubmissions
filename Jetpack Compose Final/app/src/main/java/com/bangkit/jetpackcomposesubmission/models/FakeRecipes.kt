package com.bangkit.vegalicious.models

import com.bangkit.jetpackcomposesubmission.models.Recipe

object FakeRecipes {
	val dummyRecipes = MutableList(30) { index ->
		Recipe(
			id = index.toString(),
			title = "Resep $index",
			photoUrl = "https://assets.epicurious.com/photos/63b5b03305dd27a0d03a18a6/1:1/w_1920,c_limit/Jackfruit%20curry-RECIPE.jpg",
			tags = listOf(
				"Indian",
				"Indonesian",
				"Gluten-Free",
				"Vegetarian",
				"Soy-Free",
				"Curry",
				"Western",
				"Eastern"
			),
			ingredients = listOf(
				"A pinch of ingredient number 1",
				"1 cup of lorem ipsum dolor sit amet.",
				"2 tsp of lorem",
				"3 can of ipsum",
				"10 gram of dolor",
				"1000 ton of flours",
				"Lorem ipsum dolor sit amet amet amet amet amet amettttt",
			),
			directions = listOf(
				"Combine all the ingredients for the spice paste in a small blender and blitz until smooth. You could also use a pestle and mortar, as I sometimes do. Set the spice paste aside while you start the curry.",
				"Heat the oil in a pan and add the bay leaf, dried red chiles and cumin seeds. When they start to sizzle, add the onions and cook on a low-to-medium heat for 12–15 minutes, until deep golden brown.",
				"Add the salt, turmeric and jackfruit, mixing well. Cover and cook for 10 minutes, until the jackfruit has taken on all the flavors of the sauce. Stir in the garam masala and sugar and it’s ready to serve.",
				"Combine all the ingredients for the spice paste in a small blender and blitz until smooth. You could also use a pestle and mortar, as I sometimes do. Set the spice paste aside while you start the curry.",
				"Combine all the ingredients for the spice paste in a small blender and blitz until smooth. You could also use a pestle and mortar, as I sometimes do. Set the spice paste aside while you start the curry.",
				"Heat the oil in a pan and add the bay leaf, dried red chiles and cumin seeds. When they start to sizzle, add the onions and cook on a low-to-medium heat for 12–15 minutes, until deep golden brown.",
				"Add the salt, turmeric and jackfruit, mixing well. Cover and cook for 10 minutes, until the jackfruit has taken on all the flavors of the sauce. Stir in the garam masala and sugar and it’s ready to serve.",
				"Combine all the ingredients for the spice paste in a small blender and blitz until smooth. You could also use a pestle and mortar, as I sometimes do. Set the spice paste aside while you start the curry.",
				"Combine all the ingredients for the spice paste in a small blender and blitz until smooth. You could also use a pestle and mortar, as I sometimes do. Set the spice paste aside while you start the curry.",
				"Heat the oil in a pan and add the bay leaf, dried red chiles and cumin seeds. When they start to sizzle, add the onions and cook on a low-to-medium heat for 12–15 minutes, until deep golden brown.",
				"Add the salt, turmeric and jackfruit, mixing well. Cover and cook for 10 minutes, until the jackfruit has taken on all the flavors of the sauce. Stir in the garam masala and sugar and it’s ready to serve.",
				"Combine all the ingredients for the spice paste in a small blender and blitz until smooth. You could also use a pestle and mortar, as I sometimes do. Set the spice paste aside while you start the curry.",
			),
			nutrition = mapOf(
				"fat" to 1234.2f,
				"calories" to 23.2f,
				"protein" to 334.2f,
				"sodium" to 1110f,
			),
			description = "Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet."
		)
	}
}