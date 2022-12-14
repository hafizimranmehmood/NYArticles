package com.example.nyarticles.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nyarticles.domain.models.Article
import com.example.nyarticles.domain.models.Response
import com.example.nyarticles.domain.models.Response.Loading
import com.example.nyarticles.domain.models.Response.Success
import com.example.nyarticles.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeArticleRepository: ArticleRepository {
    private val articles = LinkedHashMap<Long, Article>()
    private val observableArticles = MutableLiveData<List<Article>>()
    private val observableArticle = MutableLiveData<Article>()
    private val responseFlow = MutableStateFlow<Response>(Loading)

    private val article1 = Article(1,"title1", "byline1", "section1", "publisheddate1", 1)
    private val article2 = Article(2,"title2", "byline2", "section2", "publisheddate2", 2)
    private val article3 = Article(3,"title3", "byline3", "section3", "publisheddate3", 3)
    private val _articles = listOf(article1, article2, article3)

    private fun refresh(){
        observableArticles.value = articles.values.toList()
    }

    override suspend fun getArticles(period: Int, apiKey: String, refresh: Boolean) {
        responseFlow.emit(Loading)
        saveArticles(_articles)
        responseFlow.emit(Success)
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

    override fun observeResponse(): StateFlow<Response> = responseFlow
}