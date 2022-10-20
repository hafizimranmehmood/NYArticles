package com.example.nyarticles.domain.source

import androidx.lifecycle.LiveData
import com.example.nyarticles.domain.models.Article

interface LocalArticleDataSource {
    suspend fun saveArticles(articles: List<Article>)
    suspend fun deleteArticles()
    fun observeArticles(): LiveData<List<Article>>
    fun observeArticle(articleId: Long): LiveData<Article>
}