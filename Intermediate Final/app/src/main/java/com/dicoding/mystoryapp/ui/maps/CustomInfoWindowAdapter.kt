package com.dicoding.mystoryapp.ui.maps

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.dicoding.mystoryapp.R
import com.dicoding.mystoryapp.data.remote.response.StoryItem
import com.dicoding.mystoryapp.utils.toSnippetPreview
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindowAdapter(val context: Context, val hashMap: MutableMap<String, StoryItem>) : GoogleMap.InfoWindowAdapter {
	private val window: View = LayoutInflater.from(context).inflate(R.layout.info_window_custom, null)
	
	private fun renderWindowText(marker: Marker, view: View) {
		Log.d("CustomInfoWindowAdapter", context.toString())
		val title = marker.title
		val tvTitle = view.findViewById<TextView>(R.id.tv_infowindow_name)
		if(!title.equals("")) {
			tvTitle.text = title
		}
		
		val key = marker.snippet
		val storyItem = hashMap[key]
		val tvDescription = view.findViewById<TextView>(R.id.tv_infowindow_description)
		if(storyItem != null) {
			tvDescription.text = toSnippetPreview(storyItem.description)
		}
	}
	
	override fun getInfoContents(p0: Marker): View? {
		return null
	}
	
	override fun getInfoWindow(p0: Marker): View {
		renderWindowText(p0, window)
		return window
	}
}