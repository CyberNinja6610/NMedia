package ru.netology.nmedia.dao

import androidx.lifecycle.LiveData
import ru.netology.nmedia.entity.PostEntity

interface PostDao {
    fun getAll(): LiveData<List<PostEntity>>
    fun save(post: PostEntity)
    fun likeById(id:Long)
    fun removeById(id: Long)
    fun shareById(id: Long)
    fun getDraft(): PostEntity?
}