package com.example.nyarticles.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nyarticles.domain.models.Article

/**
 * BindingAdapter for the Articles list.
 */
@BindingAdapter("app:items")
fun setItems(recyclerView: RecyclerView, items: List<Article>?) {
    items?.let {
        (recyclerView.adapter as ArticleAdapter).submitList(items)
    }
}