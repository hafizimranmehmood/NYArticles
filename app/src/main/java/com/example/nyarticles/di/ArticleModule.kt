package com.example.nyarticles.di

import android.content.Context
import androidx.room.Room
import com.example.nyarticles.data.db.ArticleDao
import com.example.nyarticles.data.db.ArticlesDatabase
import com.example.nyarticles.data.repository.ArticleRepositoryImpl
import com.example.nyarticles.data.retrofit.ArticlesService
import com.example.nyarticles.data.source.LocalArticleDataSourceImpl
import com.example.nyarticles.data.source.RemoteArticleDataSourceImpl
import com.example.nyarticles.domain.repository.ArticleRepository
import com.example.nyarticles.domain.source.LocalArticleDataSource
import com.example.nyarticles.domain.source.RemoteArticleDataSource
import com.example.nyarticles.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * A module required by Hilt to create
 * objects of requested dependencies
 * and installing it as application
 * wise Singleton
 *
 * */

@Module
@InstallIn(SingletonComponent::class)
class ArticleModule {

    /**
     * Provides retrofit dependency
     *
     * @return Retrofit to create network api services i.e ArticleService
     * */

    @Provides
    @Singleton
    fun providesRetrofit() = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * Provides ArticlesService
     * dependency to download articles from
     * api.nytimes.com
     *
     * @param retrofit To create ArticlesService
     * @return ArticlesService to perform network service request to fetch articles
     * */

    @Provides
    @Singleton
    fun providesArticleService(retrofit: Retrofit): ArticlesService = retrofit.create(ArticlesService::class.java)

    /**
     * Provides Room database dependency
     *
     * @param context Required to build Room database
     * @return ArticlesDatabase to save articles
     * */

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): ArticlesDatabase =
        Room.databaseBuilder(context, ArticlesDatabase::class.java, Constants.DATABASE_NAME).build()

    /**
     * Provides ArticleDao dependency
     *
     * @param articlesDatabase Required to get article dao
     * @return ArticleDao to perform crud operations on article
     * */

    @Provides
    @Singleton
    fun providesArticleDao(articlesDatabase: ArticlesDatabase) = articlesDatabase.getArticleDao()

    @Provides
    @Singleton
    fun providesRemoteArticleDataSource(
        articlesService: ArticlesService
    ): RemoteArticleDataSource = RemoteArticleDataSourceImpl(articlesService)


    @Provides
    @Singleton
    fun providesLocalArticleDataSource(
        articleDao: ArticleDao
    ): LocalArticleDataSource = LocalArticleDataSourceImpl(articleDao)

    /**
     * Provides ArticleRepository dependency
     *
     * @param localArticleDataSource required by repository to perform curd operation on local database
     * @param remoteArticleDataSource required to download articles from api.nytimes.com
     * @return ArticleRepository to perform operations on data layer
     *
     * */

    @Provides
    @Singleton
    fun providesArticleRepository(
        localArticleDataSource: LocalArticleDataSource,
        remoteArticleDataSource: RemoteArticleDataSource
    ): ArticleRepository = ArticleRepositoryImpl(localArticleDataSource, remoteArticleDataSource)
}