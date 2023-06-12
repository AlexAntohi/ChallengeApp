package com.example.challengeapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class User (

    @PrimaryKey(autoGenerate = true) val userId: Int,
    @ColumnInfo(name = "username" ) val username: String,
    @ColumnInfo(name = "password" ) val password: String
    )
{

}