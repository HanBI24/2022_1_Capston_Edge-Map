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