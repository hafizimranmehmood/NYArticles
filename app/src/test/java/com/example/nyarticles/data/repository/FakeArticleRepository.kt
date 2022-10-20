package com.example.nyarticles.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nyarticles.domain.models.Article
import com.example.nyarticles.domain.models.Response
import com.example.nyarticles.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeArticleRepository: ArticleRepository {
    private val articles = LinkedHashMap<Long, Article>()
    private val observableArticles = MutableLiveData<List<Article>>()
    private val observableArticle = MutableLiveData<Article>()

    private fun refresh(){
        observableArticles.value = articles.values.toList()
    }

    override suspend fun getArticles(period: Int, apiKey: String, refresh: Boolean) {
    }

    override suspend fun deleteArticles() {
        articles.clear()
        refresh()
    }

    override suspend fun saveArticles(articles: List<Article>) {
        articles.forEach {
            this.articles[it.id] = it
        }
        refresh()
    }

    override fun observeArticles(): LiveData<List<Article>> = observableArticles

    override fun observeArticle(articleId: Long): LiveData<Article> {
        observableArticle.value = articles[articleId]
        return observableArticle
    }

    override fun observeResponse(): StateFlow<Response> {
        return MutableStateFlow(Response.Loading)
    }
}