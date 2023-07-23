package com.dicoding.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.dicoding.githubuser.databinding.ActivityUserDetailsBinding
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsBinding
    private val userDetailsViewModel by viewModels<UserDetailsViewModel>()

    companion object {
        const val EXTRA_LOGIN = "extra_login"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val login = intent.getStringExtra(EXTRA_LOGIN)
        login?.let { userDetailsViewModel.getDetails(it) }

        userDetailsViewModel.userDetailsResponse.observe(this) {
            binding.tvName.text = it?.name
            binding.tvUsername.text = it?.login
            val followers = it?.followers.toString() + " Followers"
            val following = it?.following.toString() + " Following"
            binding.tvFollowers.text = followers
            binding.tvFollowing.text = following
            Glide.with(binding.ivDetails)
                .load(it?.avatarUrl)
                .into(binding.ivDetails)
        }

        userDetailsViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val sectionsPagerAdapter = login?.let { SectionsPagerAdapter(this, it) }
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.viewPager.currentItem = 0
        val viewPagerHeight = resources.displayMetrics.heightPixels - 5 * binding.tabs.height
        binding.viewPager.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            viewPagerHeight
        )

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_user_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.close -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}