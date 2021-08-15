package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean = false,
    val likes: Long = 0,
    val shares: Long = 0,
    val video: String? = "",
    val isDraft: Boolean = false
)