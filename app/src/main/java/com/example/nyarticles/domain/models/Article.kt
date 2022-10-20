package com.example.nyarticles.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Article entity class
 *
 * contains article data
 *
 * */

@Entity
data class Article(
    @PrimaryKey
    val id: Long,
    val title: String,
    val byline: String,
    val section: String,
    val publishedDate: String,
    val date: Long
)