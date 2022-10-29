package com.example.nyarticles.ui

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.nyarticles.R.id
import com.example.nyarticles.domain.models.Article
import com.example.nyarticles.domain.repository.ArticleRepository
import com.example.nyarticles.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ArticleFragmentTest{

    @Inject
    lateinit var articleRepository: ArticleRepository
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val article1 = Article(1,"title1", "byline1", "section1", "publisheddate1", System.currentTimeMillis())
    private val article2 = Article(2,"title2", "byline2", "section2", "publisheddate2", System.currentTimeMillis())
//    private val article3 = Article(3,"title3", "byline3", "section3", "publisheddate3", 3)
    private val articles = listOf(article1, article2)

    @Before
    fun setUp(){
        hiltRule.inject()
    }

    @After
    fun tearDown(){
    }

    @Test
    fun clickArticle_navigateToDetailArticleFragment() = runTest {
        articleRepository.saveArticles(articles)

        val navController = mock(NavController::class.java)
        val scenario = launchFragmentInHiltContainer<ArticleFragment>{
            Navigation.setViewNavController(requireView(), navController)
        }

//        onView(withId(id.articles))
//            .perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(0, click()))
//        onView(withId(id.articles)).perform(RecyclerViewActions.scrollTo<ViewHolder>(hasDescendant(
//            withText("title1"))))
        onView(withId(id.articles)).perform(RecyclerViewActions.actionOnItem<ViewHolder>(
            hasDescendant(withText("title1")), click()
        ))
        verify(navController).navigate(ArticleFragmentDirections.actionArticleFragmentToArticleDetailFragment(article1.id))
    }

    @Test
    fun article_displayedInUi() = runTest {
        articleRepository.saveArticles(articles)

        launchFragmentInHiltContainer<ArticleFragment>{
        }

        onView(withId(id.articles)).check(matches(hasDescendant(withText("title1"))))
        onView(withId(id.articles)).check(matches(hasDescendant(withText("byline2"))))
        onView(withId(id.articles)).check(matches(hasDescendant(withText("section1"))))
        onView(withId(id.articles)).check(matches(hasDescendant(withText("publisheddate2"))))
    }
}