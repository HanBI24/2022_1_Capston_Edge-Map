package com.example.hotplenavigation.di

import android.content.Context
import com.example.hotplenavigation.database.BookmarkFragmentDao
import com.example.hotplenavigation.database.BookmarkFragmentDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Room DB를 사용하기 위한 Module
// 보일러 플레이트 코드 감소 및 의존성을 제거하기 위해
// DI Library 중 하나인 Dagger-Hilt를 사용
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideGetAppDatabaseBookmarkFragment(
        @ApplicationContext applicationContext: Context
    ): BookmarkFragmentDatabase =
        BookmarkFragmentDatabase.getSearchFragmentInstance(applicationContext)

    @Singleton
    @Provides
    fun provideGetAppDaoBookmarkFragment(
        bookmarkFragmentDatabase: BookmarkFragmentDatabase
    ): BookmarkFragmentDao =
        bookmarkFragmentDatabase.getBookmarkFragmentDao()
}
