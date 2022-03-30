package com.example.hiltmvvm.di

import com.example.hiltmvvm.repository.MainRepository
import com.example.hiltmvvm.retrofit.BlogRetrofit
import com.example.hiltmvvm.retrofit.NetworkMapper
import com.example.hiltmvvm.room.BlogDao
import com.example.hiltmvvm.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMainRepository(
        blogDao: BlogDao,
        retrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository{
        return MainRepository(blogDao, retrofit, cacheMapper, networkMapper)
    }
}