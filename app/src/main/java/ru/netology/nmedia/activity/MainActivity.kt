package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostsAdapter(onInteractionListener = object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

        })

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.save.setOnClickListener {
            with(binding) {
                if (content.text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        content.context.getString(R.string.can_not_be_empty),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.changeContent(content.text.toString())
                viewModel.save()

                content.setText("")
                editText.text = ""
                editedPost.visibility = View.GONE
                content.clearFocus()
                AndroidUtils.hideKeyboard(this.content)
            }
        }

        binding.cancelEdit.setOnClickListener {
            with(binding) {
                content.setText("")
                editText.text = ""
                editedPost.visibility = View.GONE
                content.clearFocus()
                AndroidUtils.hideKeyboard(this.content)
            }
        }


        viewModel.edited.observe(this) {
            if (it.id != 0L) {
                with(binding) {
                    content.requestFocus()
                    content.setText(it.content)
                    editText.text = it.content
                    editedPost.visibility = View.VISIBLE
                }
            }
        }
    }


}