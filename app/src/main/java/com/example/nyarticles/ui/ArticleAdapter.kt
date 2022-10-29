package com.example.nyarticles.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nyarticles.databinding.ArticleItemBinding
import com.example.nyarticles.domain.models.Article
import com.example.nyarticles.ui.ArticleAdapter.ArticleViewHolder

class ArticleAdapter(private val viewModel: ArticleViewModel) : ListAdapter<Article, ArticleViewHolder>(DIFF_UTIL_CALLBACK) {

    companion object{
        private val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<Article>(){
            override fun areItemsTheSame(oldItem: Article, newItem: Article) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Article, newItem: Article) = oldItem == newItem
        }
    }

    class ArticleViewHolder private constructor(private val binding: ArticleItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: ArticleViewModel, article: Article){
            binding.article = article
            binding.viewmodel = viewModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) = ArticleViewHolder(ArticleItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArticleViewHolder.from(parent)

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) = holder.bind(viewModel, getItem(position))
}