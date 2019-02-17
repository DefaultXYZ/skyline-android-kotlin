package com.defaultxyz.skyline.domain.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.defaultxyz.skyline.domain.model.Location

@Entity(tableName = "t_location")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    @ColumnInfo(name = "email") val userEmail: String
)