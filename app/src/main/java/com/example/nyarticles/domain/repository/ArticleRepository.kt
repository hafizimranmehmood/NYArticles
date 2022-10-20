package com.example.nyarticles.domain.repository

import androidx.lifecycle.LiveData
import com.example.nyarticles.domain.models.Article
import com.example.nyarticles.domain.models.Response
import kotlinx.coroutines.flow.StateFlow

interface ArticleRepository {
    suspend fun getArticles(period: Int, apiKey: String, refresh: Boolean)
    suspend fun deleteArticles()
    suspend fun saveArticles(articles: List<Article>)
    fun observeArticles(): LiveData<List<Article>>
    fun observeArticle(articleId: Long): LiveData<Article>
    fun observeResponse(): StateFlow<Response>
}