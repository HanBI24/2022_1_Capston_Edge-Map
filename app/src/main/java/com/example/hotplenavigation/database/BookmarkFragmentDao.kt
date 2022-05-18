package com.example.hotplenavigation.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkFragmentDao {

    @Query("SELECT DISTINCT * FROM frag_bookmark")
    fun getAllData(): Flow<List<BookmarkFragmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(word: BookmarkFragmentEntity)

    @Query("DELETE FROM frag_bookmark WHERE title = :title")
    suspend fun deleteByTitle(title: String)

    @Query("SELECT DISTINCT * FROM frag_bookmark WHERE `like`")
    fun getAllLikeData(): Flow<List<BookmarkFragmentEntity>>
}
