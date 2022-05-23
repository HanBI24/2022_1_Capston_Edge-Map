package com.example.hotplenavigation.repository

import com.example.hotplenavigation.database.BookmarkFragmentDao
import com.example.hotplenavigation.database.BookmarkFragmentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// Room DB를 사용하여 데이터를 받아오는 Repository
class BookmarkFragmentRepository @Inject constructor(
    private val bookmarkFragmentDao: BookmarkFragmentDao
) {
    // 모든 데이터 가져오기
    fun getAllData(): Flow<List<BookmarkFragmentEntity>> =
        bookmarkFragmentDao.getAllData()

    // 데이터 삽입
    suspend fun insertData(
        bookmarkFragmentEntity: BookmarkFragmentEntity
    ) {
        bookmarkFragmentDao.insertData(bookmarkFragmentEntity)
    }

    // 데이터 삭제
    suspend fun deleteByTitle(
        number: String
    ) {
        bookmarkFragmentDao.deleteByTitle(number)
    }

    // 좋아요 누른 모든 데이터 가져오기
    fun getAllLikeData() = bookmarkFragmentDao.getAllLikeData()
}
