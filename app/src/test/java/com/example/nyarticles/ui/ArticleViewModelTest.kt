package com.example.nyarticles.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nyarticles.MainCoroutineRule
import com.example.nyarticles.data.repository.FakeArticleRepository
import com.example.nyarticles.domain.models.Article
import com.example.nyarticles.domain.usecases.*
import com.example.nyarticles.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArticleViewModelTest{

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get: Rule
    val mainCoroutineRule = MainCoroutineRule()
    private lateinit var fakeArticleRepository: FakeArticleRepository
    private lateinit var viewModel: ArticleViewModel
    private val article1 = Article(1,"title1", "byline1", "section1", "publisheddate1", 1)
    private val article2 = Article(2,"title2", "byline2", "section2", "publisheddate2", 2)
    private val article3 = Article(3,"title3", "byline3", "section3", "publisheddate3", 3)
    private val articles = listOf(article1, article2, article3)

    @Before
    fun setUp(){
        fakeArticleRepository = FakeArticleRepository()
        val interactor = Interactor(
            GetArticles(fakeArticleRepository),
            ObserveArticles(fakeArticleRepository),
            ObserveArticle(fakeArticleRepository),
            DeleteArticles(fakeArticleRepository),
            ObserveResponse(fakeArticleRepository)
        )
        viewModel = ArticleViewModel(interactor, Dispatchers.Main)
    }

    @Test
    fun articles_shouldReturnAllArticles() = runTest{
        fakeArticleRepository.saveArticles(articles)
        val result = viewModel.articles.getOrAwaitValue()
        assertThat(result).isEqualTo(articles)
    }

    @Test
    fun articles_secondArticleTitleShouldMatch() = runTest{
        fakeArticleRepository.saveArticles(articles)
        val result = viewModel.articles.getOrAwaitValue()
        assertThat(result[1].title).isEqualTo(article2.title)
    }

    @Test
    fun articles_sizeShouldMatch() = runTest{
        fakeArticleRepository.saveArticles(articles)
        val result = viewModel.articles.getOrAwaitValue()
        assertThat(result.size).isEqualTo(articles.size)
    }

    @Test
    fun loadArticle_shouldLoadArticleById() = runTest{
        fakeArticleRepository.saveArticles(articles)
        viewModel.loadArticle(article2.id)
        val article = viewModel.article.getOrAwaitValue()
        assertThat(article).isEqualTo(article2)
    }

    @Test
    fun getArticles_responseShouldWorkProperly() = mainCoroutineRule.runBlockingTest{
        viewModel.refresh(emptyList())
        assertThat(viewModel.loading.getOrAwaitValue()).isFalse()
        val articles = viewModel.articles.getOrAwaitValue()
        assertThat(articles.size).isEqualTo(3)
    }
}