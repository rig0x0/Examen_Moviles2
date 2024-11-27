package com.example.remc_exa_ll.network

import com.example.remc_exa_ll.services.CommentService
import com.example.remc_exa_ll.services.PostService
import com.example.remc_exa_ll.services.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClienteRetrofit {
    private val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val getInstanciaRetrofit: UserService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)
    }

    val getInstancePostRetrofit: PostService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostService::class.java)
    }

    val getInstanceCommentRetrofit: CommentService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CommentService::class.java)
    }
}