import androidx.lifecycle.Transformations
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity
import ru.netology.nmedia.repository.PostRepository

class PostRepositoryImpl(
    private val dao: PostDao,
) : PostRepository {
    override fun getAll() = Transformations.map(dao.getAll()) { list ->
        list.map {
            with(it) {
                Post(
                    id = id,
                    author = author,
                    content = content,
                    published = published,
                    likedByMe = likedByMe,
                    likes = likes,
                    shares = shares,
                    video = video,
                    isDraft = isDraft
                )
            }
        }
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
    }

    override fun getDraft(): Post? {
        return dao.getDraft()?.let {
            with(it) {
                Post(
                    id = id,
                    author = author,
                    content = content,
                    published = published,
                    likedByMe = likedByMe,
                    likes = likes,
                    shares = shares,
                    video = video,
                    isDraft = isDraft
                )
            }
        }
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }
}