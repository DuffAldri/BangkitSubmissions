package com.dicoding.githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.databinding.FragmentFollowersFollowingBinding

class FollowersFollowingFragment : Fragment() {

    private var login: String? = null
    private var index: Int? = null

    private var _binding: FragmentFollowersFollowingBinding? = null
    private val binding get() = _binding!!
    private val followersFollowingViewModel by viewModels<FollowersFollowingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            index = it.getInt(ARG_SECTION_NUMBER, 0)
            login = it.getString(ARG_LOGIN)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersFollowingBinding.inflate(inflater, container, false)

        followersFollowingViewModel.listUser.observe(viewLifecycleOwner) { listFollow ->
            if (listFollow != null && listFollow.isNotEmpty()) {
                setUserData(listFollow)
            } else {
                val text = when(arguments?.getInt(ARG_SECTION_NUMBER, 0)) {
                    0 -> "This user is not followed by anyone"
                    1 -> "This user follows no one"
                    else -> "Query error"
                }
                Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
            }
        }

        binding.rvUser.layoutManager = LinearLayoutManager(context)

        followersFollowingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (login != null && index != null) {
            followersFollowingViewModel.findUser(login!!, index!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUserData(users: List<FollowersFollowingResponseItem>) {
        val listUser = ArrayList<UserItem>()
        for(i in users) {
            val user = UserItem(i.avatarUrl, i.login)
            listUser.add(user)
        }
        val adapter = FollowersFollowingAdapter(listUser)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_LOGIN = "login"
    }
}