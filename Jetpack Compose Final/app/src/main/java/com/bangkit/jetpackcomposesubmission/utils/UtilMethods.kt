package com.bangkit.jetpackcomposesubmission.utils

import okhttp3.internal.toImmutableList

public fun splitToList(input: String): List<String> {
	val stringArray = input.split(";")
	val stringList = mutableListOf<String>()
	
	for(str in stringArray) {
		stringList.add(str.trim())
	}
	return stringList.toImmutableList()
}