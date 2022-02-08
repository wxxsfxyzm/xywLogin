package com.carlyu.xywlogin.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) var userId: Long?,

    @ColumnInfo(name = "user_name") var userName: String,

    @ColumnInfo(name = "user_passwd") var userPasswd: String,

    @ColumnInfo(name = "net_type") var netType: String,

    @ColumnInfo(name = "ip_type") var ipType: String,

    @ColumnInfo(name = "isRememberChecked") var isRememberChecked: Boolean,

    @ColumnInfo(name = "isAutoLoginChecked") var isAutoLoginChecked: Boolean,


    )
