package com.example.nyarticles.domain.models

/**
 * Remote article Result data class
 *
 * */
data class Result(
    val `abstract`: String,
    val adx_keywords: String,
    val asset_id: Long,
    val byline: String,
    val column: Any,
    val eta_id: Int,
    val id: Long,
    val nytdsection: String,
    val org_facet: List<String>,
    val per_facet: List<String>,
    val published_date: String,
    val section: String,
    val source: String,
    val subsection: String,
    val title: String,
    val type: String,
    val updated: String,
    val uri: String,
    val url: String
)