package com.dicoding.githubuser

import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    fun getUser(@Query("q") username: String): Call<UserResponse>

    @GET("users/{username}")
    fun getUserDetails(@Path("username") username: String): Call<UserDetailsResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<FollowersFollowingResponseItem>>

    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<FollowersFollowingResponseItem>>

}