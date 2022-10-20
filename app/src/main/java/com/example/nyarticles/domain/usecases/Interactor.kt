package com.example.nyarticles.domain.usecases

import javax.inject.Inject

class Interactor @Inject constructor(
    val getArticles: GetArticles,
    val observeArticles: ObserveArticles,
    val observeArticle: ObserveArticle,
    val deleteArticles: DeleteArticles,
    val observeResponse: ObserveResponse
)