package com.example.nyarticles.data.source

import androidx.lifecycle.LiveData
import com.example.nyarticles.data.db.ArticleDao
import com.example.nyarticles.domain.models.Article
import com.example.nyarticles.domain.source.LocalArticleDataSource

class LocalArticleDataSourceImpl(private val articleDao: ArticleDao): LocalArticleDataSource {

    /**
     * Save articles in local database
     *
     * @param articles articles list to save in the database
     *
     * */
    override suspend fun saveArticles(articles: List<Article>) = articleDao.insertArticles(articles)

    /**
     * Delete all articles from local database
     *
     * */
    override suspend fun deleteArticles() = articleDao.deleteArticles()

    /**
     * Observe articles in local database
     *
     * */
    override fun observeArticles(): LiveData<List<Article>> = articleDao.getArticles()

    /**
     * Observe article by id in local database
     *
     * @param articleId articleId to observe the related article
     * */
    override fun observeArticle(articleId: Long): LiveData<Article> = articleDao.getArticleById(articleId)
}