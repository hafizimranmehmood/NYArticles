package com.example.nyarticles.data.repository

import androidx.lifecycle.LiveData
import com.example.nyarticles.domain.models.Article
import com.example.nyarticles.domain.models.Response
import com.example.nyarticles.domain.repository.ArticleRepository
import com.example.nyarticles.domain.source.LocalArticleDataSource
import com.example.nyarticles.domain.source.RemoteArticleDataSource
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Singleton

@Singleton
class ArticleRepositoryImpl(
    private val localDataSource: LocalArticleDataSource,
    private val remoteDataSource: RemoteArticleDataSource
) : ArticleRepository {

    override fun observeResponse(): StateFlow<Response> = remoteDataSource.progressStateFlow

    /**
     * Fetch articles from remote api
     * and save those to db
     *
     * */
    override suspend fun getArticles(period: Int, apiKey: String, refresh: Boolean) {
        val result = remoteDataSource.getArticles(period, apiKey, refresh)
        if(!result.isNullOrEmpty())
            saveArticles(result)
    }

    /**
     * Delete all articles from local database
     *
     * */
    override suspend fun deleteArticles() = localDataSource.deleteArticles()

    /**
     * Save given articles in local database
     *
     * */
    override suspend fun saveArticles(articles: List<Article>) = localDataSource.saveArticles(articles)

    /**
     * Get articles from database to observe
     *
     * @return LiveData of Article List
     * */
    override fun observeArticles(): LiveData<List<Article>> = localDataSource.observeArticles()

    /**
     * Get article by id from database to observe
     *
     * @return LiveData of Article
     * */
    override fun observeArticle(articleId: Long): LiveData<Article> = localDataSource.observeArticle(articleId)
}