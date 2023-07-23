package com.dicoding.tokolaptop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.tokolaptop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener{
//    private lateinit var rvProducts: RecyclerView
    private val list = ArrayList<Product>()
    private var viewType = 1 //1 == List, 2 == Grid
//    private lateinit var changeViewBtn: Button
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvProducts.setHasFixedSize(true)
        binding.changeView.setOnClickListener(this)
        binding.rvProducts.setOnClickListener(this)

        list.addAll(getListHeroes())
        showRecyclerList()
    }

    private fun showRecyclerList() {
        binding.rvProducts.layoutManager = LinearLayoutManager(this)
        val listProductAdapter = ListProductAdapter(list)
        binding.rvProducts.adapter = listProductAdapter
    }

    private fun getListHeroes(): Collection<Product> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataPrice = resources.getStringArray(R.array.data_price)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataCPU = resources.getStringArray(R.array.data_cpu)
        val dataMemory = resources.getStringArray(R.array.data_memory)
        val dataStorage = resources.getStringArray(R.array.data_storage)
        val dataGPU = resources.getStringArray(R.array.data_gpu)
        val dataDisplay = resources.getStringArray(R.array.data_display)
        val listProduct = ArrayList<Product>()

        for(i in dataName.indices) {
            val product = Product(
                name = dataName[i],
                price = dataPrice[i],
                description = dataDescription[i],
                photo = dataPhoto.getResourceId(i, -1),
                cpu = dataCPU[i],
                memory = dataMemory[i],
                storage = dataStorage[i],
                gpu = dataGPU[i],
                display = dataDisplay[i])
            listProduct.add(product)
        }

        return listProduct
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.change_view -> {
                when(viewType) {
                    1 -> {
                        viewType = 2
                        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)
                    }
                    2 -> {
                        viewType = 1
                        binding.rvProducts.layoutManager = LinearLayoutManager(this)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.about_page -> {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
//    override fun onItemClicked(view: View, product: Product) {
//        val moveProductDetail = Intent(, ProductDetailActivity::class.java)
//
//        startActivity(moveProductDetail)
//    }

}