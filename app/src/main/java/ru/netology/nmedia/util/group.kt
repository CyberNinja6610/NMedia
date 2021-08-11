package ru.netology.nmedia.util

import android.view.View
import androidx.constraintlayout.widget.Group

fun Group.addOnClickListener(listener: (view: View) -> Unit) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}