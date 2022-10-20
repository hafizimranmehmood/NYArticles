package com.example.nyarticles.ui

import androidx.lifecycle.*
import com.example.nyarticles.domain.models.Article
import com.example.nyarticles.domain.models.Response
import com.example.nyarticles.domain.models.Response.Loading
import com.example.nyarticles.domain.usecases.Interactor
import com.example.nyarticles.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val interactor: Interactor): ViewModel() {

    private val _articleId = MutableLiveData<Long>()

    val article: LiveData<Article> = _articleId.switchMap { articleId ->
        interactor.observeArticle(articleId)
    }

    private val _openArticleDetail = MutableLiveData<Long>()
    val openArticleDetail: LiveData<Long> = _openArticleDetail

    private val _progressResponse: StateFlow<Response> = interactor.observeResponse()
    val articles: LiveData<List<Article>> = interactor.observeArticles()

    val loading: LiveData<Boolean> = _progressResponse.asLiveData().map {
        it is Loading
    }

    val error: LiveData<Boolean> = _progressResponse.asLiveData().map {
        it is Error
    }

    fun openArticleDetail(articleId: Long) {
        _openArticleDetail.value = articleId
    }

    fun loadArticle(articleId: Long){
        _articleId.value = articleId
    }

    fun refresh(articles: List<Article>){
        //Refresh using remote API if local db is empty or after a day
        val shouldRefresh = articles.isEmpty() || (System.currentTimeMillis() - articles[0].date) >= Constants.ONE_DAY_THRESH_HOLD
        viewModelScope.launch(Dispatchers.IO){
            if(shouldRefresh)
                interactor.deleteArticles()

            interactor.getArticles(1, "your-api-key", shouldRefresh)
        }
    }
}