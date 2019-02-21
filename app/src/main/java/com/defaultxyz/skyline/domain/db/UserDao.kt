package com.defaultxyz.skyline.domain.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.defaultxyz.skyline.domain.db.entity.UserEntity
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM t_user LIMIT 1")
    fun getUser(): Single<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity)

    @Query("DELETE FROM t_user")
    fun clearUser()
}