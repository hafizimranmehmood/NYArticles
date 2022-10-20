package com.example.nyarticles.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nyarticles.domain.models.Article
import com.example.nyarticles.domain.source.LocalArticleDataSource

class FakeLocalDataSource(private val articles: MutableList<Article> = mutableListOf()): LocalArticleDataSource {
    private val _articles = MutableLiveData<List<Article>>()
    private val _article = MutableLiveData<Article>()
    override suspend fun saveArticles(articles: List<Article>) {
        this.articles.addAll(articles)
        _articles.value = this.articles
    }

    override suspend fun deleteArticles() {
        articles.clear()
        _articles.value = this.articles
    }

    override fun observeArticles(): LiveData<List<Article>> {
        return _articles
    }

    override fun observeArticle(articleId: Long): LiveData<Article> {
        val res = articles.filter { it.id == articleId }
        if(res.isNotEmpty())
            _article.value = res[0]
        return _article
    }
}