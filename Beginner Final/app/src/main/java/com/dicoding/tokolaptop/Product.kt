package com.dicoding.tokolaptop

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class Product(
    val name: String,
    val price: String,
    val description: String,
    val photo: Int,
    val cpu: String,
    val memory: String,
    val storage: String,
    val gpu: String,
    val display: String
) : Parcelable

