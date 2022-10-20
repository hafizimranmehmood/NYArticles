package com.example.nyarticles.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nyarticles.domain.models.Article

/**
 * Articles database
 * To persists all the articles
 *
 * */
@Database(entities = [Article::class], version = 1)
abstract class ArticlesDatabase: RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao
}