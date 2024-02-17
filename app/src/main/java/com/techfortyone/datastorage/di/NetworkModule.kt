package com.techfortyone.datastorage.di

import com.techfortyone.datastorage.data.remote.BASE_URL
import com.techfortyone.datastorage.data.remote.PostsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
            .baseUrl(
                BASE_URL
            ).build()

    @Provides
    @Singleton
    fun providePostApi(retrofit: Retrofit) = retrofit.create(PostsApi::class.java)

}

