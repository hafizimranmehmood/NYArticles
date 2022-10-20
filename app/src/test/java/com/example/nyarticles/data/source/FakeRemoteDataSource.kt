package com.example.nyarticles.data.source

import com.example.nyarticles.domain.models.Article
import com.example.nyarticles.domain.models.Response
import com.example.nyarticles.domain.models.Response.Loading
import com.example.nyarticles.domain.source.RemoteArticleDataSource
import kotlinx.coroutines.flow.MutableStateFlow

class FakeRemoteDataSource(private val articles: MutableList<Article> = mutableListOf()): RemoteArticleDataSource {
    private val _progressStateFlow = MutableStateFlow<Response>(Loading)
    override val progressStateFlow = _progressStateFlow
    override suspend fun getArticles(period: Int, apiKey: String, refresh: Boolean): List<Article> = articles
}