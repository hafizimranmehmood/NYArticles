package com.example.nyarticles.data.source

import com.example.nyarticles.data.retrofit.ArticlesService
import com.example.nyarticles.domain.models.Article
import com.example.nyarticles.domain.models.Response
import com.example.nyarticles.domain.models.Response.*
import com.example.nyarticles.domain.models.asDomainModel
import com.example.nyarticles.domain.source.RemoteArticleDataSource
import kotlinx.coroutines.flow.MutableStateFlow

class RemoteArticleDataSourceImpl(private val articlesService: ArticlesService) : RemoteArticleDataSource {
    private val _progressStateFlow = MutableStateFlow<Response>(Loading)
    /**
     * To monitor progress of articles downloading
     *
     */
    override val progressStateFlow = _progressStateFlow

    /**
     * Get articles from remote service api.nytimes.com
     *
     * @param period required to represents how far back, in days
     * @param apiKey required to provide api key to api.nytimes.com
     * @param apiKey required to check do we really need to pull the data
     * @return ArticleRepository to perform operations on data layer
     *
     * */
    override suspend fun getArticles(period: Int, apiKey: String, refresh: Boolean): List<Article> {
        if(!refresh)
            _progressStateFlow.emit(Success)
        else{
            _progressStateFlow.emit(Loading)
            val result = articlesService.getArticles(period, apiKey)
            if(result.isSuccessful){
                result.body()?.let {
                    return it.asDomainModel()
                    _progressStateFlow.emit(Success)
                }
            }else
                _progressStateFlow.emit(Error)
        }
        return emptyList()
    }
}