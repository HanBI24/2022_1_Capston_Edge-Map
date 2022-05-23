package com.example.hotplenavigation.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Room DB를 사용하기 위한 Entity 설정
@Entity(tableName = "frag_bookmark")
class BookmarkFragmentEntity(
    val title: String,      // 이름
    val address: String,    // 주소
    var like: Boolean       // 좋아요 여부
) {
    // 기본 키(자동 증가)
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    var id: Int = 0
}
