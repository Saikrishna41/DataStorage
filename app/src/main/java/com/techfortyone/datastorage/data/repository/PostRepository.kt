package com.techfortyone.datastorage.data.repository

import android.content.Context
import android.util.Log
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
            val postFromCache = readFromCache()
            if (postFromCache?.isNotEmpty() == true) {
                 postFromCache
            }
            else {
                Log.d("TAGSS", "read from web service: ")
                val result = postsApi.getPosts()
                storeDataInFile(result.body())
                storeDataInCache(result.body())
                result.body()
            }
        }
    }

    fun storeDataInFile(posts: List<PostsItemDto>?) {
        val file = File(context.filesDir, "posts.json")
        val gson = Gson()
        val listType =  object : TypeToken<List<PostsItemDto>>() {}.type
        val fileContents = gson.toJson(posts, listType)
        file.writeText(fileContents)
    }

    fun storeDataInCache(posts: List<PostsItemDto>?) {
        val file = File(context.cacheDir, "posts.json")
        val gson = Gson()
        val listType =  object : TypeToken<List<PostsItemDto>>() {}.type
        val fileContents = gson.toJson(posts, listType)
        file.writeText(fileContents)
    }

    fun readFromCache() : List<PostsItemDto>? {
        Log.d("TAGSS", "readFromCache: ")
        val cacheFile = File(context.cacheDir, "posts.json")
        val json = if (cacheFile.exists()) {
            cacheFile.readText()
        } else {
            null
        }
        return if (json == null) {
            emptyList()
        }
        else {
            val gson = Gson()
            val listType =  object : TypeToken<List<PostsItemDto>>() {}.type
            val fileContents = gson.fromJson<List<PostsItemDto>>(json, listType)
            fileContents
        }
    }
}