package com.defaultxyz.skyline.domain.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "t_location")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    @ColumnInfo(name = "email") val userEmail: String
)