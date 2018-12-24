package com.zl.core.db.user

import androidx.room.*


/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/24 15:24.<br/>
 */
@Dao
interface UserDao {

    @Query("SELECT * FROM user where uid = :uid")
    fun queryUserById(uid: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: User)

    @Delete
    fun delete(user: User)
}