package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryFile

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likedByMe = false,
    published = "",
    video = ""
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryFile(application)
    val data = repository.getAll()
    val edited = MutableLiveData(empty)
    val opened = MutableLiveData(empty)
    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty;
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
            if (edited.value?.id == opened.value?.id) {
                opened.value = edited.value?.copy()
            }
        }
    }

    fun open(post: Post) {
        opened.value = post
    }

    fun likeById(id: Long) {
        repository.likeById(id)
        syncOpened(id)
    }

    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)

    private fun syncOpened(id: Long) {
        opened.value = data.value?.find {
            it.id == id
        }?.copy()
    }
}