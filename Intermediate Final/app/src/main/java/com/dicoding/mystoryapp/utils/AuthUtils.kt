package com.dicoding.mystoryapp.utils

class AuthUtils {
	
	companion object {
		fun isEmailValid(email: String): Boolean {
			val regex = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
			return regex.matches(email)
		}
	}
}