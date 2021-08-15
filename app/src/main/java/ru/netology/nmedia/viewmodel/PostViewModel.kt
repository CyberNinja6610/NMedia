package ru.netology.nmedia.viewmodel

import PostRepositoryImpl
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository

private val empty = Post(
    id = 0,
    content = "",
    author = "Me",
    likedByMe = false,
    published = "Now",
    video = "",
    shares = 0,
    likes = 0,
    isDraft = true
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryImpl(
        AppDb.getInstance(context = application).postDao()
    )

    var data = repository.getAll()
    val edited = MutableLiveData(empty)
    var draft = repository.getDraft()?: empty


    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
        draft = repository.getDraft()?: empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (it.content == text) {
                return
            }
            edited.value = edited.value?.copy(content = text)
        }
    }

    fun changeIsDraft(isDraft: Boolean) {
        edited.value?.let {
            edited.value = edited.value?.copy(isDraft = isDraft)
        }
    }


    fun likeById(id: Long) {
        repository.likeById(id)
    }

    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)

}