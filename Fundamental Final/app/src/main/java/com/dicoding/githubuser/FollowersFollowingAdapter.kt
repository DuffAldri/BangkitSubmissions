package com.dicoding.githubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.databinding.ItemUserBinding

class FollowersFollowingAdapter(private val listUser: List<UserItem>): RecyclerView.Adapter<FollowersFollowingAdapter.UserViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return UserViewHolder(binding)
    }
    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(viewHolder: UserViewHolder, position: Int) {
        viewHolder.binding.tvItem.text = listUser[position].username
        Glide.with(viewHolder.itemView.context)
            .load(listUser[position].imageUrl)
            .into(viewHolder.binding.ivItem)

        viewHolder.binding.rvCard.setOnClickListener {
            val userDetails = Intent(viewHolder.itemView.context, UserDetailsActivity::class.java)
            userDetails.putExtra(UserDetailsActivity.EXTRA_LOGIN, listUser[position].username)
            viewHolder.itemView.context.startActivities(arrayOf(userDetails))
        }
    }

    class UserViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
}

