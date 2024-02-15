package com.techfortyone.datastorage.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.techfortyone.datastorage.data.model.PostsItemDto
import com.techfortyone.datastorage.data.remote.PostsApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class PostRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val postsApi: PostsApi
) {

    suspend fun getPosts(): List<PostsItemDto>? {
        return withContext(Dispatchers.IO) {
            val result = postsApi.getPosts()
            storeDataInFile(result.body())
            result.body()
        }
    }

    fun storeDataInFile(posts: List<PostsItemDto>?) {
        val file = File(context.filesDir, "posts.json")
        val gson = Gson()
        val listType =  object : TypeToken<List<PostsItemDto>>() {}.type
        val fileContents = gson.toJson(posts, listType)
        file.writeText(fileContents)
    }
}