package com.example.nyarticles.domain.usecases

import com.example.nyarticles.domain.repository.ArticleRepository
import javax.inject.Inject

class GetArticles @Inject constructor(private val articleRepository: ArticleRepository) {

    suspend operator fun invoke(period: Int, apiKey: String, refresh: Boolean) =
        articleRepository.getArticles(period, apiKey, refresh)
}