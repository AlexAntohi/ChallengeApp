package com.example.challengeapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Challenge
    (
    @PrimaryKey(autoGenerate = true) val challengeId : Int,
    @ColumnInfo val name: String,
    @ColumnInfo val description: String
            )