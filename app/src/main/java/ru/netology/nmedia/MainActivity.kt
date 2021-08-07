package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.formatToString

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Меняем карьеру через образование",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false
        )

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likeText.text = post.likes.formatToString()
            shareText.text = post.shares.formatToString()

            if (post.likedByMe) {
                likeIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                likeIcon.setImageResource(R.drawable.ic_round_favorite_border_24)
                post.likes = post.likes--
                likeText.text = post.likes.formatToString()
            }

            likeIcon.setOnClickListener {
                if (it is ImageButton) {
                    if (post.likedByMe) {
                        it.setImageResource(R.drawable.ic_round_favorite_border_24)
                        post.likes -= 1
                        post.likedByMe = false
                        likeText.text = post.likes.formatToString()
                    } else {
                        it.setImageResource(R.drawable.ic_baseline_favorite_24)
                        post.likes += 1
                        post.likedByMe = true
                        likeText.text = post.likes.formatToString()
                    }
                }
            }

            shareIcon.setOnClickListener {
                if (it is ImageButton) {
                    post.shares += 1
                    shareText.text = post.shares.formatToString()
                }
            }


        }

    }
}