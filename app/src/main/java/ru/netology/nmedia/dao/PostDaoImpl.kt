package ru.netology.nmedia.dao
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.nmedia.entity.PostEntity


@Dao
interface PostDaoImpl: PostDao {
    @Query("SELECT * FROM PostEntity WHERE isDraft = 0 ORDER BY id DESC")
    override fun getAll(): LiveData<List<PostEntity>>

    @Insert
    fun insert(post: PostEntity)

    @Query("UPDATE PostEntity SET content = :content, isDraft = :isDraft WHERE id = :id")
    fun updateContentById(id: Long, content: String, isDraft: Boolean)

    override fun save(post: PostEntity) =
        if (post.id == 0L) insert(post) else updateContentById(post.id, post.content, post.isDraft)

    @Query("""
        UPDATE PostEntity SET
        likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
        likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
        WHERE id = :id
        """)
    override fun likeById(id: Long)

    @Query("""
        UPDATE PostEntity SET
        shares = shares + 1
        WHERE id = :id
        """)
    override fun shareById(id: Long)

    @Query("DELETE FROM PostEntity WHERE id = :id")
    override fun removeById(id: Long)

    @Query("SELECT * FROM PostEntity WHERE isDraft = 1 ORDER BY id DESC LIMIT 1")
    override fun getDraft(): PostEntity
}