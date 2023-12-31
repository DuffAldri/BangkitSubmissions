package com.dicoding.mystoryapp.ui.addstory

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dicoding.mystoryapp.R
import com.dicoding.mystoryapp.databinding.ActivityStoryAddBinding
import com.dicoding.mystoryapp.ui.main.MainActivity
import com.dicoding.mystoryapp.utils.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class StoryAddActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityStoryAddBinding
	private val storyAddViewModel by viewModels<StoryAddViewModel>()
	private lateinit var fusedLocationClient: FusedLocationProviderClient
	private var lastLocation: Location? = null
	private var isUseLocation = false
	
	private var getFile: File? = null
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityStoryAddBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		if (!allPermissionsGranted()) {
			ActivityCompat.requestPermissions(
				this,
				REQUIRED_PERMISSIONS,
				REQUEST_CODE_PERMISSIONS
			)
		}
		
		fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
		
		binding.cameraXButton.setOnClickListener { startCameraX() }
		binding.galleryButton.setOnClickListener { startGallery() }
		binding.locationSwitch.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
			isUseLocation = isChecked
			getMyLocation()
		}
		binding.buttonAdd.setOnClickListener { uploadImage() }
	}
	
	private val requestPermissionLauncher =
		registerForActivityResult(
			ActivityResultContracts.RequestMultiplePermissions()
		) { permissions ->
			when {
				permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
					getMyLocation()
				}
				permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
					getMyLocation()
				}
				else -> {
					// No location access granted.
				}
			}
		}
	
	private fun checkPermission(permission: String): Boolean {
		return ContextCompat.checkSelfPermission(
			this,
			permission
		) == PackageManager.PERMISSION_GRANTED
	}
	
	private fun getMyLocation() {
		if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
			checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
		){
			fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
				if (location != null) {
					lastLocation = location
				} else {
					Toast.makeText(
						this,
						getString(R.string.location_not_found),
						Toast.LENGTH_SHORT
					).show()
				}
			}
		} else {
			requestPermissionLauncher.launch(
				arrayOf(
					Manifest.permission.ACCESS_FINE_LOCATION,
					Manifest.permission.ACCESS_COARSE_LOCATION
				)
			)
		}
	}
	
	private fun startGallery() {
		val intent = Intent()
		intent.action = Intent.ACTION_GET_CONTENT
		intent.type = "image/*"
		val chooser = Intent.createChooser(intent, "Choose a Picture")
		launcherIntentGallery.launch(chooser)
	}
	
	private fun startCameraX() {
		val intent = Intent(this, CameraActivity::class.java)
		launcherIntentCameraX.launch(intent)
	}
	
	private val launcherIntentCameraX = registerForActivityResult(
		ActivityResultContracts.StartActivityForResult()
	) {
		if (it.resultCode == CAMERA_X_RESULT) {
			val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
				it.data?.getSerializableExtra("picture", File::class.java)
			} else {
				@Suppress("DEPRECATION")
				it.data?.getSerializableExtra("picture")
			} as? File
			
			val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
			
			myFile?.let { file ->
				rotateFile(file, isBackCamera)
				getFile = file
				binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
			}
		}
	}
	
	private val launcherIntentGallery = registerForActivityResult(
		ActivityResultContracts.StartActivityForResult()
	) { result ->
		if (result.resultCode == RESULT_OK) {
			val selectedImg = result.data?.data as Uri
			
			selectedImg.let { uri ->
				val myFile = uriToFile(uri, this@StoryAddActivity)
				getFile = myFile
				binding.previewImageView.setImageURI(uri)
			}
		}
	}
	
	private fun uploadImage() {
		if(getFile != null && binding.edAddDescription.text.isNotEmpty()) {
			val description = binding.edAddDescription.text.toString()
			val file = reduceFileImage(getFile as File)
			
			val requestDescription =
				description.toRequestBody("text/plain".toMediaType())
			val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
			
			val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
				"photo",
				file.name,
				requestImageFile
			)
			
			if(!isUseLocation)
				storyAddViewModel.getUploadResponse(imageMultipart, requestDescription)
			else {
				val requestLat = lastLocation?.latitude?.toFloat().toString().toRequestBody("text/plain".toMediaType())
				val requestLon = lastLocation?.longitude?.toFloat().toString().toRequestBody("text/plain".toMediaType())
				
				storyAddViewModel.getUploadResponse(
					imageMultipart,
					requestDescription,
					requestLat,
					requestLon
				)
			}
			
			storyAddViewModel.upload.observe(this) {
				if(!it.error) {
					Toast.makeText(this, getString(R.string.upload_success), Toast.LENGTH_SHORT).show()
					val intent = Intent(this, MainActivity::class.java)
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
					startActivity(intent)
					
				} else {
					Toast.makeText(this, getString(R.string.upload_failure), Toast.LENGTH_SHORT)
						.show()
				}
			}
			
			storyAddViewModel.errorMessage.observe(this) { errorMessage ->
				if(errorMessage != null) {
					Toast.makeText(this, getString(R.string.upload_failure), Toast.LENGTH_SHORT)
						.show()
					storyAddViewModel.clearErrorMessage()
				}
			}
			
			storyAddViewModel.isLoading.observe(this) {
				showLoading(it)
			}
		}
	}

	private fun showLoading(isLoading: Boolean) {
		binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
	}
	
	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<String>,
		grantResults: IntArray
	) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		if (requestCode == REQUEST_CODE_PERMISSIONS) {
			if (!allPermissionsGranted()) {
				Toast.makeText(
					this,
					getString(R.string.no_permission),
					Toast.LENGTH_SHORT
				).show()
				finish()
			}
		}
	}
	
	private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
		ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
	}
	
	companion object {
		const val CAMERA_X_RESULT = 200
		
		private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
		private const val REQUEST_CODE_PERMISSIONS = 10
	}

}