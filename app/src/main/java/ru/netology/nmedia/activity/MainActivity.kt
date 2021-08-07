package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.formatToString
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likeIcon.setImageResource(if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_round_favorite_border_24)
                likeText.text = post.likes.formatToString()
                shareText.text = post.shares.formatToString()
            }
        }

        binding.likeIcon.setOnClickListener {
            viewModel.like()
        }

        binding.shareIcon.setOnClickListener {
            viewModel.share()
        }

/*        with(binding) {
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
                    post.likedByMe = !post.likedByMe
                    it.setImageResource(if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_round_favorite_border_24)
                    if (post.likedByMe) post.likes++ else post.likes--
                    likeText.text = post.likes.formatToString()
                }
            }

            shareIcon.setOnClickListener {
                if (it is ImageButton) {
                    post.shares++
                    shareText.text = post.shares.formatToString()
                }
            }

        }*/
    }


}