package com.dicoding.githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: UserDetailsActivity, private val login: String) : FragmentStateAdapter(activity) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowersFollowingFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowersFollowingFragment.ARG_SECTION_NUMBER, position)
            putString(FollowersFollowingFragment.ARG_LOGIN, login)
        }

        return fragment
    }


}