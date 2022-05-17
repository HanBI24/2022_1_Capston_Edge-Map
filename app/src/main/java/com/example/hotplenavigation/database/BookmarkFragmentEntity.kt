package com.example.hotplenavigation.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "frag_bookmark")
class BookmarkFragmentEntity (
    val title: String,
    val address: String,
    var like: Boolean
) {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    var id: Int = 0
}
