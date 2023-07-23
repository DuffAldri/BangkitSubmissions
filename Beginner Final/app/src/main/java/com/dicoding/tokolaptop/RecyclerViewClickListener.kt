package com.dicoding.tokolaptop

import android.view.View

interface RecyclerViewClickListener {
    fun onItemClicked(view: View, product: Product)
}