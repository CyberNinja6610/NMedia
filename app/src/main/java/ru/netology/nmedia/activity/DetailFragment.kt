package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.databinding.FragmentDetailBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.helpres.formatToString
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.util.addOnClickListener
import ru.netology.nmedia.viewmodel.PostViewModel

class DetailFragment : Fragment() {

    companion object {
        var Bundle.postId: String? by StringArg
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentDetailBinding.inflate(
            inflater,
            container,
            false
        )

        val onInteractionListener = object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, post.content)
                }

                Intent.createChooser(intent, getString(R.string.share_description))
                startActivity(intent)
                viewModel.shareById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
                findNavController().navigateUp()
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
                findNavController().navigate(
                    R.id.action_detailPostFragment_to_newPostFragment,
                    Bundle().apply {
                        textArg = post.content
                    }
                )
            }

            override fun onVideoPlay(post: Post) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(post.video)));

            }

        }


        viewModel.data
            .observe(viewLifecycleOwner) { posts ->
                with(binding.cardPost) {
                    arguments?.postId?.let { postId ->
                        posts.find { it.id == postId.toLong() }?.let { post ->
                            author.text = post.author
                            published.text = post.published
                            content.text = post.content
                            likeIcon.isChecked = post.likedByMe
                            likeIcon.text = post.likes.formatToString()
                            shareIcon.text = post.shares.formatToString()
                            videoGroup.visibility =
                                if (post.video.isNullOrBlank()) View.GONE else View.VISIBLE

                            likeIcon.setOnClickListener {
                                onInteractionListener.onLike(post)
                            }

                            shareIcon.setOnClickListener {
                                onInteractionListener.onShare(post)
                            }

                            videoGroup.addOnClickListener {
                                onInteractionListener.onVideoPlay(post)
                            }

                            menu.setOnClickListener {
                                val popup = PopupMenu(it.context, it).apply {
                                    inflate(R.menu.options_post)
                                    setOnMenuItemClickListener { menuItem ->
                                        when (menuItem.itemId) {
                                            R.id.menu_remove -> {
                                                onInteractionListener.onRemove(post)
                                                true
                                            }
                                            R.id.menu_edit -> {
                                                onInteractionListener.onEdit(post)
                                                true
                                            }
                                            else -> false
                                        }
                                    }
                                }
                                popup.show()
                            }
                        }
                    }
                }
            }

        return binding.root

    }


}