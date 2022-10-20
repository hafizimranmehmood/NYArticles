package com.example.nyarticles.domain.source

import com.example.nyarticles.domain.models.Article
import com.example.nyarticles.domain.models.Response
import kotlinx.coroutines.flow.StateFlow

interface RemoteArticleDataSource {
    val progressStateFlow: StateFlow<Response>
    suspend fun getArticles(period: Int, apiKey: String, refresh: Boolean): List<Article>
}