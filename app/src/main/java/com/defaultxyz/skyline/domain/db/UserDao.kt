package com.defaultxyz.skyline.domain.db

import androidx.room.*
import com.defaultxyz.skyline.domain.db.entity.UserEntity
import io.reactivex.Flowable

@Dao
interface UserDao {

    @Query("SELECT * FROM t_user LIMIT 1")
    fun getUser(): Flowable<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity)

    @Query("DELETE FROM t_user")
    fun clearUser()
}