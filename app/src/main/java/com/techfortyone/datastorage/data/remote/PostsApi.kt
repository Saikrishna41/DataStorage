package com.techfortyone.datastorage.data.remote

import com.techfortyone.datastorage.data.model.PostsItemDto
import retrofit2.Response
import retrofit2.http.GET

const val BASE_URL = "https://jsonplaceholder.typicode.com"
interface PostsApi {
    @GET("/posts")
    suspend fun getPosts() : Response<List<PostsItemDto>>
}