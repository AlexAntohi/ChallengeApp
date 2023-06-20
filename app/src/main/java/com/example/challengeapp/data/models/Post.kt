package com.example.challengeapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post (
    @PrimaryKey(autoGenerate = true) val postId: Int,
    @ColumnInfo val userId: Int,
    @ColumnInfo val challengeId: Int,
    @ColumnInfo val numberOfLikes: Int,
    @ColumnInfo val videoUrl: String
        ) {
}