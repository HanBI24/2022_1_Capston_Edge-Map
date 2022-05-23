package com.example.hotplenavigation.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// Room DB를 사용하기 위한 DAO 인터페이스 작성
@Dao
interface BookmarkFragmentDao {

    // 모든 데이터 얻기
    @Query("SELECT DISTINCT * FROM frag_bookmark")
    fun getAllData(): Flow<List<BookmarkFragmentEntity>>

    // 데이터 추가
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(word: BookmarkFragmentEntity)

    // 이름을 기준으로 데이터 제거
    @Query("DELETE FROM frag_bookmark WHERE title = :title")
    suspend fun deleteByTitle(title: String)

    // 좋아요가 표시된 데이터만 얻기
    @Query("SELECT DISTINCT * FROM frag_bookmark WHERE `like`")
    fun getAllLikeData(): Flow<List<BookmarkFragmentEntity>>
}
