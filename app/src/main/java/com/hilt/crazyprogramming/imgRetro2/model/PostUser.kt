package com.hilt.crazyprogramming.imgRetro2.model

data class PostUser(
    val name: String,
    val phone: String,
    val gender: String
)

data class PostUserResponse(
    val name: String,
    val phone: String,
    val gender: String,
    val id: Int
)

data class PostImageResponse(
    val name: String,
    val image: String,
    val id: Int
)