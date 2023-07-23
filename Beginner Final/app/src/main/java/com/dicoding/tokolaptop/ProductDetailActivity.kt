package com.dicoding.tokolaptop

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ProductDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PRODUCT = "extra_product"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val detailProduct = if(Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Product>("extra_product")
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("extra_product")
        }

        val photoDetail: ImageView = findViewById(R.id.imageView)
        val nameDetail: TextView = findViewById(R.id.tv_title)
        val priceDetail: TextView = findViewById(R.id.tv_price)
//        val qtyDetail: TextView = findViewById(R.id.tv_quantity)
        val descDetail: TextView = findViewById(R.id.deskripsi_isi)
        val cpuSpecDetail: TextView = findViewById(R.id.content_specs_cpu)
        val memorySpecDetail: TextView = findViewById(R.id.content_specs_memory)
        val storageSpecDetail: TextView = findViewById(R.id.content_specs_storage)
        val gpuSpecDetail: TextView = findViewById(R.id.content_specs_GPU)
        val displaySpecDetail: TextView = findViewById(R.id.content_specs_display)

        if(detailProduct != null) {
            photoDetail.setImageResource(detailProduct?.photo!!)
            nameDetail.text = detailProduct.name.toString()
            priceDetail.text = detailProduct.price.toString()
            descDetail.text = detailProduct.description.toString()
            cpuSpecDetail.text = detailProduct.cpu.toString()
            memorySpecDetail.text = detailProduct.memory.toString()
            storageSpecDetail.text = detailProduct.storage.toString()
            gpuSpecDetail.text = detailProduct.gpu.toString()
            displaySpecDetail.text = detailProduct.display.toString()
        }
    }
}