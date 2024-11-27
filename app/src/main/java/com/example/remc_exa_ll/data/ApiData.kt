package com.example.remc_exa_ll.data

data class ApiUser(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: ApiAddress,
    val company: ApiCompany
)

data class ApiAddress(
    val city: String,
    val street: String,
    val suite: String,
    val zipcode: String,
    val geo: ApiGeo
)

data class ApiGeo(
    val lat: String,
    val lng: String
)

data class ApiCompany(
    val name: String,
    val catchPhrase: String,
    val bs: String
)

data class ApiPost(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

data class ApiComment(
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
)