package com.example.nyarticles.domain.usecases

import com.example.nyarticles.domain.repository.ArticleRepository
import javax.inject.Inject

class ObserveResponse @Inject constructor(private val articleRepository: ArticleRepository) {

    operator fun invoke() = articleRepository.observeResponse()
}