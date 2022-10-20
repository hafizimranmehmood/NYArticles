package com.example.nyarticles.domain.models

/**
 * Remote Articles data class
 *
 * */
data class Articles(
    val copyright: String,
    val num_results: Int,
    val results: List<Result>,
    val status: String
)

fun Articles.asDomainModel() : List<Article> {
    val time = System.currentTimeMillis()
    return results.map {
        Article(
            it.id,
            it.title,
            it.byline,
            it.section,
            it.published_date,
            time
        )
    }
}