package com.example.nyarticles

import com.example.nyarticles.data.repository.FakeAndroidArticleRepository
import com.example.nyarticles.di.RepositoryModule
import com.example.nyarticles.domain.repository.ArticleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
class FakeRepositoryModule {

    @Singleton
    @Provides
    fun providesFakeArticleRepository(): ArticleRepository = FakeAndroidArticleRepository()

    @Provides
    @Singleton
    fun providesCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Main
}