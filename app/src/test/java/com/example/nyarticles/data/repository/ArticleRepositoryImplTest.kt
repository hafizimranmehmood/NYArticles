package com.example.nyarticles.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nyarticles.data.source.FakeLocalDataSource
import com.example.nyarticles.data.source.FakeRemoteDataSource
import com.example.nyarticles.domain.models.Article
import com.example.nyarticles.domain.repository.ArticleRepository
import com.example.nyarticles.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArticleRepositoryImplTest{

    private val article1 = Article(1,"title1", "byline1", "section1", "publisheddate1", 1)
    private val article2 = Article(2,"title2", "byline2", "section2", "publisheddate2", 2)
    private val article3 = Article(3,"title3", "byline3", "section3", "publisheddate3", 3)
    private val article4 = Article(4,"title4", "byline4", "section4", "publisheddate4", 4)
    private val remoteArticles = listOf<Article>(article1, article2, article3)
    private val localArticles = listOf<Article>(article4)
    private val allArticles = mutableListOf<Article>(article1, article2, article3, article4)

    private lateinit var remoteDataSource: FakeRemoteDataSource
    private lateinit var localDataSource: FakeLocalDataSource

    private lateinit var articleRepository: ArticleRepository

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        remoteDataSource = FakeRemoteDataSource(remoteArticles.toMutableList())
        localDataSource = FakeLocalDataSource(localArticles.toMutableList())
        articleRepository = ArticleRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Test
    fun getArticles_getAllArticlesFromRemoteDataSource_shouldEqualToAllArticles() = runTest{
        articleRepository.getArticles(1,"", true)
        val articles = articleRepository.observeArticles().getOrAwaitValue().sortedBy { it.id }
        assertThat(articles).isEqualTo(allArticles)
    }

    @Test
    fun observeArticles_checkSizeOfArticleList_shouldEqualTo4() = runTest{
        articleRepository.getArticles(1,"", true)
        val articles = articleRepository.observeArticles().getOrAwaitValue()
        assertThat(articles.size).isEqualTo(allArticles.size)
    }

    @Test
    fun observeArticle_observeArticleById() = runTest{
        articleRepository.getArticles(1,"", true)
        val article = articleRepository.observeArticle(2).getOrAwaitValue()
        assertThat(article).isEqualTo(article2)
    }

    @Test
    fun deleteArticles_deleteAllArticles_shouldReturnEmptyList() = runTest{
        articleRepository.getArticles(1,"", true)
        articleRepository.deleteArticles()
        val articles = articleRepository.observeArticles().getOrAwaitValue()
        assertThat(articles.isEmpty()).isTrue()
    }
}