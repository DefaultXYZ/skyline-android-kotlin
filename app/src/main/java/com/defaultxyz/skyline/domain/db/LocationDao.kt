package com.defaultxyz.skyline.domain.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.defaultxyz.skyline.domain.db.entity.LocationEntity
import io.reactivex.Single

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(locations: List<LocationEntity>)

    @Query("SELECT * FROM t_location")
    fun getAllLocations(): Single<List<LocationEntity>>

    @Query("SELECT * FROM t_location WHERE email = :email")
    fun getAllLocationsByUserEmail(email: String): Single<List<LocationEntity>>
}