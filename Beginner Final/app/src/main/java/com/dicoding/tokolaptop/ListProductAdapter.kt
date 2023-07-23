package com.dicoding.tokolaptop

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.tokolaptop.databinding.ItemRowLaptopBinding

class ListProductAdapter(private val listProduct: ArrayList<Product>) : RecyclerView.Adapter<ListProductAdapter.ListViewHolder>(){
    private var listener: RecyclerViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {

//        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_laptop, parent, false)
        val binding = ItemRowLaptopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listProduct.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, price, description, photo) = listProduct[position]

        holder.binding.imgItemPhoto.setImageResource(photo)
        holder.binding.tvItemName.text = name
        holder.binding.tvItemDescription.text = description
        holder.binding.tvItemPrice.text = price


        holder.binding.itemCard.setOnClickListener {
//            listener?.onItemClicked(it, listProduct[position])
            val moveProductDetail = Intent(holder.itemView.context, ProductDetailActivity::class.java)
            moveProductDetail.putExtra(ProductDetailActivity.EXTRA_PRODUCT, listProduct[position])
            holder.itemView.context.startActivities(arrayOf(moveProductDetail))
        }
    }

    class ListViewHolder(var binding: ItemRowLaptopBinding) : RecyclerView.ViewHolder(binding.root)

//    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
//        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
//        val tvPrice: TextView = itemView.findViewById(R.id.tv_item_price)
//        val tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
//    }
}