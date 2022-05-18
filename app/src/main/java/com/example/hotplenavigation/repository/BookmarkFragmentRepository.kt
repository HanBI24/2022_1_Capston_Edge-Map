package com.example.hotplenavigation.repository

import com.example.hotplenavigation.database.BookmarkFragmentDao
import com.example.hotplenavigation.database.BookmarkFragmentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkFragmentRepository @Inject constructor(
    private val bookmarkFragmentDao: BookmarkFragmentDao
) {
    fun getAllData(): Flow<List<BookmarkFragmentEntity>> =
        bookmarkFragmentDao.getAllData()

    suspend fun insertData(
        bookmarkFragmentEntity: BookmarkFragmentEntity
    ) {
        bookmarkFragmentDao.insertData(bookmarkFragmentEntity)
    }

    suspend fun deleteByTitle(
        number: String
    ) {
        bookmarkFragmentDao.deleteByTitle(number)
    }

    fun getAllLikeData() = bookmarkFragmentDao.getAllLikeData()
}
