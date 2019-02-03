package com.defaultxyz.skyline.domain.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "t_user")
data class UserEntity(
    @PrimaryKey val id: Int = 1,
    val email: String,
    val password: String,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "date_of_birth") val dateOfBirth: String
)