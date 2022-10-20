package com.example.nyarticles.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nyarticles.domain.models.Article
import com.example.nyarticles.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ArticleDaoTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var articlesDatabase: ArticlesDatabase
    private lateinit var articleDao: ArticleDao

    private val article1 = Article(1,"title1", "byline1", "section1", "publisheddate1", 1)
    private val article2 = Article(2,"title2", "byline2", "section2", "publisheddate2", 2)
    private val article3 = Article(3,"title3", "byline3", "section3", "publisheddate3", 3)
    private val articles = listOf(article1, article2, article3)

    @Before
    fun setUp(){
        articlesDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArticlesDatabase::class.java
        ).allowMainThreadQueries().build()
        articleDao = articlesDatabase.getArticleDao()
    }

    @After
    fun cleanUp(){
        articlesDatabase.close()
    }

    @Test
    fun insertArticles_getArticles_shouldReturnSame() = runTest{
        articleDao.insertArticles(articles)
        val result = articleDao.getArticles().getOrAwaitValue()
        assertThat(result).isEqualTo(articles)
    }

    @Test
    fun insertArticles_getArticleById_shouldReturnSame() = runTest{
        articleDao.insertArticles(articles)
        val result = articleDao.getArticleById(article3.id).getOrAwaitValue()
        assertThat(result).isEqualTo(article3)
    }

    @Test
    fun insertArticles_deleteArticles_shouldReturnEmpty() = runTest{
        articleDao.insertArticles(articles)
        articleDao.deleteArticles()
        val result = articleDao.getArticles().getOrAwaitValue()
        assertThat(result.isEmpty()).isTrue()
    }
}