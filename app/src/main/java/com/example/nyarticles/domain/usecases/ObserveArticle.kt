package com.example.nyarticles.domain.usecases

import com.example.nyarticles.domain.repository.ArticleRepository
import javax.inject.Inject

class ObserveArticle @Inject constructor(private val articleRepository: ArticleRepository) {
    operator fun invoke(articleId: Long) = articleRepository.observeArticle(articleId)
}