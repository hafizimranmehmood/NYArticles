package com.example.nyarticles.data.retrofit

import com.example.nyarticles.domain.models.Articles
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Article download service
 * download articles from api.nytimes.com
 *
 * */
interface ArticlesService {
    @GET("/svc/mostpopular/v2/viewed/{period}.json")
    suspend fun getArticles(@Path("period") period: Int, @Query("api-key") apiKey: String): Response<Articles>
}