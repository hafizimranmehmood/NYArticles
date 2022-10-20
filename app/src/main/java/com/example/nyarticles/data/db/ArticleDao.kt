package com.example.nyarticles.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nyarticles.domain.models.Article

/**
 * Article DAO
 * To perform CRUD operations on articles
 *
 * */

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(article: List<Article>)

    @Query("Select * from Article")
    fun getArticles(): LiveData<List<Article>>

    @Query("Select * from Article where id = :articleId")
    fun getArticleById(articleId: Long): LiveData<Article>

    @Query("Delete from Article")
    suspend fun deleteArticles()
}