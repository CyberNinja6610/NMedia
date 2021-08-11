package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        val content = intent.getStringExtra(Intent.EXTRA_TEXT)
        setContentView(binding.root)
        binding.edit.setText(content)

        binding.ok.setOnClickListener {
            val intent = Intent()
            if (binding.edit.text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED)
            } else {
                val context = binding.edit.text.toString()
                intent.putExtra(Intent.EXTRA_TEXT, context)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }
}