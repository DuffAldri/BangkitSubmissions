package com.dicoding.mystoryapp.ui.maps

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.dicoding.mystoryapp.R
import com.dicoding.mystoryapp.data.remote.response.StoryItem
import com.dicoding.mystoryapp.databinding.ActivityMapsBinding
import com.dicoding.mystoryapp.ui.detailstory.StoryDetailsActivity
import com.dicoding.mystoryapp.utils.ViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
	
	private lateinit var mMap: GoogleMap
	private lateinit var binding: ActivityMapsBinding
	private lateinit var fusedLocationClient: FusedLocationProviderClient
	private val mutableMap: MutableMap<String, StoryItem> = mutableMapOf()
	
	private val mapsViewModel: MapsViewModel by viewModels {
		ViewModelFactory(this)
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		binding = ActivityMapsBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		val mapFragment = supportFragmentManager
			.findFragmentById(R.id.map) as SupportMapFragment
		mapFragment.getMapAsync(this)

		mapsViewModel.isLoading.observe(this) {
			showLoading(it)
		}
		
		fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
	}
	
	override fun onMapReady(googleMap: GoogleMap) {
		mMap = googleMap
		
		mMap.uiSettings.isZoomControlsEnabled = true
		mMap.uiSettings.isIndoorLevelPickerEnabled = true
		mMap.uiSettings.isCompassEnabled = true
		mMap.uiSettings.isMapToolbarEnabled = true
		
		mapsViewModel.listStory.observe(this) { listStory ->
			if(listStory != null && listStory.isNotEmpty())
				listStory.forEach { storyItem ->
					val latLng = LatLng(storyItem.lat.toDouble(), storyItem.lon.toDouble())
					mMap.addMarker(MarkerOptions()
						.position(latLng)
						.title(storyItem.name)
						.snippet(storyItem.id)
						.icon(vectorToBitmap(R.drawable.ic_personpin))
					)
					mutableMap[storyItem.id] = storyItem
				}
			else
				Toast.makeText(this@MapsActivity, getString(R.string.failed_get_stories), Toast.LENGTH_SHORT).show()
		}
		
		mMap.setOnInfoWindowClickListener {
			val storyItem = mutableMap[it.snippet]
			if(storyItem != null) {
				val intent = Intent(this@MapsActivity, StoryDetailsActivity::class.java)
				intent.putExtra(StoryDetailsActivity.STORY_ID, storyItem.id)
				intent.putExtra(StoryDetailsActivity.STORY_NAME, storyItem.name)
				intent.putExtra(StoryDetailsActivity.STORY_PHOTO, storyItem.photoUrl)
				startActivity(intent)
			}
		}
		
		mMap.setInfoWindowAdapter(CustomInfoWindowAdapter(this@MapsActivity, mutableMap))
		
		getMyLocation()
		setMapStyle()
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
		if     (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
			checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
		){
			fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
				if (location != null) {
					mMap.isMyLocationEnabled = true
					val latLng = LatLng(location.latitude, location.longitude)
					mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
				} else {
					Toast.makeText(
						this@MapsActivity,
						"Location is not found. Try Again",
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
	
	private fun setMapStyle() {
		try {
			val success =
				mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
			if (!success) {
				Log.e(TAG, "Style parsing failed.")
			}
		} catch (exception: Resources.NotFoundException) {
			Log.e(TAG, "Can't find style. Error: ", exception)
		}
	}
	
	private fun showLoading(isLoading: Boolean) {
		binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
	}
	
	private fun vectorToBitmap(@DrawableRes id: Int): BitmapDescriptor {
		val vectorDrawable = ResourcesCompat.getDrawable(resources, id, null)
		if (vectorDrawable == null) {
			Log.e("BitmapHelper", "Resource not found")
			return BitmapDescriptorFactory.defaultMarker()
		}
		val bitmap = Bitmap.createBitmap(
			vectorDrawable.intrinsicWidth,
			vectorDrawable.intrinsicHeight,
			Bitmap.Config.ARGB_8888
		)
		val canvas = Canvas(bitmap)
		vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
		vectorDrawable.draw(canvas)
		return BitmapDescriptorFactory.fromBitmap(bitmap)
	}
	
	companion object {
		const val TAG = "MapsActivity"
	}
}