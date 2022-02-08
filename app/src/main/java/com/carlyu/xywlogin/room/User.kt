package com.carlyu.xywlogin.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) var userId: Long,

    @ColumnInfo(name = "user_name") var userName: String,

    @ColumnInfo(name = "user_passwd") var userPasswd: String

)
