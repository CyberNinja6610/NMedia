package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dao.PostDaoImpl
import ru.netology.nmedia.dto.Post

class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)
    private var draft = MutableLiveData<Post?>(null)

    init {
        posts = dao.getAll()
        draft.value = dao.getDraft()
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun save(post: Post) {
        dao.save(post)
        data.value = dao.getAll()
        draft.value = dao.getDraft()
    }

    override fun getDraft(): Post? {
        return dao.getDraft()
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
        data.value = dao.getAll()
        draft.value = dao.getDraft()
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
        data.value = dao.getAll()
        draft.value = dao.getDraft()
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
        data.value = dao.getAll()
        draft.value = dao.getDraft()
    }
}