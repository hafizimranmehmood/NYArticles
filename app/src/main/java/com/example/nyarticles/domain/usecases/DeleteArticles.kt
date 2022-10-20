package com.example.nyarticles.domain.usecases

import com.example.nyarticles.domain.repository.ArticleRepository
import javax.inject.Inject

class DeleteArticles @Inject constructor(private val articleRepository: ArticleRepository) {

    suspend operator fun invoke() = articleRepository.deleteArticles()
}