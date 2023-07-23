package com.dicoding.mystoryapp.ui.auth.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mystoryapp.R
import com.dicoding.mystoryapp.data.remote.retrofit.ApiConfig
import com.dicoding.mystoryapp.databinding.ActivityLoginBinding
import com.dicoding.mystoryapp.ui.auth.register.RegisterActivity
import com.dicoding.mystoryapp.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityLoginBinding
	private lateinit var sharedPreferences: SharedPreferences
	private val loginViewModel by viewModels<LoginViewModel>()
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityLoginBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		supportActionBar?.hide()
		
		sharedPreferences = getSharedPreferences("mystoryapp-data", MODE_PRIVATE)
		
		val auth = sharedPreferences.getString("authToken", null)
		if(auth != null) {
			ApiConfig.setAuth(auth)
			val intent = Intent(this@LoginActivity, MainActivity::class.java)
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
			startActivity(intent)
		}
		
		binding.buttonSignin.setOnClickListener {
			if(binding.edLoginEmail.error == null && binding.edLoginPassword.error == null) {
				val email = binding.edLoginEmail.text.toString()
				val password = binding.edLoginPassword.text.toString()
		
				loginViewModel.getLoginInfo(email, password)
				
				loginViewModel.login.observe(this) {
					if(!it.error) {
						val editor = sharedPreferences.edit()
						editor.putString("authToken", it.loginResult.token)
						editor.putString("name", it.loginResult.name)
						editor.putString("userId", it.loginResult.userId)
						editor.apply()
						
						Toast.makeText(this@LoginActivity, "You are signed in!", Toast.LENGTH_SHORT).show()
						
						val intent = Intent(this@LoginActivity, MainActivity::class.java)
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
						startActivity(intent)
					} else
						Toast.makeText(this, getString(R.string.invalid_credentials), Toast.LENGTH_SHORT).show()
				}
				
				loginViewModel.errorMessage.observe(this) { errorMessage ->
					if(errorMessage != null) {
						val message = if(errorMessage == LoginViewModel.INVALID_CREDENTIALS) R.string.invalid_credentials else R.string.failure
						Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
						loginViewModel.clearErrorMessage()
					}
				}
			}
		}
		
		binding.tvSignUpNow.setOnClickListener {
			val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
			startActivity(intent)
		}
		
		loginViewModel.isLoading.observe(this) {
			showLoading(it)
		}
	}
	
	private fun showLoading(isLoading: Boolean) {
		binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
	}
}