package com.example.nyarticles.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.example.nyarticles.R
import com.example.nyarticles.domain.models.Article
import com.example.nyarticles.domain.repository.ArticleRepository
import com.example.nyarticles.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ArticleDetailFragmentTest{

    @Inject
    lateinit var articleRepository: ArticleRepository
    @get: Rule
    val hiltRule = HiltAndroidRule(this)
    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val article1 = Article(1,"title1", "byline1", "section1", "publisheddate1", 1)
    private val articles = listOf(article1)

    @Before
    fun setUp(){
        hiltRule.inject()
    }

    @Test
    fun articleDetail_displayedInUi() = runTest {
        articleRepository.saveArticles(articles)
        val bundle = ArticleDetailFragmentArgs(article1.id).toBundle()
        launchFragmentInHiltContainer<ArticleDetailFragment>(bundle){

        }

        onView(withId(R.id.title)).check(matches(isDisplayed()))
        onView(withId(R.id.title)).check(matches(withText("title1")))
        onView(withId(R.id.by_line)).check(matches(isDisplayed()))
        onView(withId(R.id.by_line)).check(matches(withText("byline1")))
        onView(withId(R.id.section)).check(matches(isDisplayed()))
        onView(withId(R.id.section)).check(matches(withText("section1")))
        onView(withId(R.id.published_date)).check(matches(isDisplayed()))
        onView(withId(R.id.published_date)).check(matches(withText("publisheddate1")))
    }
}