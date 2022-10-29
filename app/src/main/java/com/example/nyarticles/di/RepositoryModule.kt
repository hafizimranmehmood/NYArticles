package com.example.nyarticles.di

import com.example.nyarticles.data.repository.ArticleRepositoryImpl
import com.example.nyarticles.domain.repository.ArticleRepository
import com.example.nyarticles.domain.source.LocalArticleDataSource
import com.example.nyarticles.domain.source.RemoteArticleDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

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

    @Provides
    @Singleton
    fun providesCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}