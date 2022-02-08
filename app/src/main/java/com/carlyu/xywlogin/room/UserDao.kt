package com.carlyu.xywlogin.room

import androidx.room.*

@Dao
interface UserDao {

    @Query("select * from users where userId = :id")
    fun getUserById(id: Long): User

    @Query("select * from users where user_name= :userName")
    fun getUserByUserName(userName: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)// ABORT / IGNORE
    fun addUser(user: User)

    @Update
    fun updateUserByUser(user: User): Int

    @Query("update users set user_name = :updateName where userId =  :id")
    fun update(id: Long, updateName: String)

    @Delete
    fun deleteUserByUser(user: User)

    @Query("delete  from users where userId = :id ")
    fun deleteUserById(id: Long)


}