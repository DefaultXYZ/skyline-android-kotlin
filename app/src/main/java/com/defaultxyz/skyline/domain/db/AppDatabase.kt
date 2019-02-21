package com.defaultxyz.skyline.domain.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.defaultxyz.skyline.domain.db.entity.LocationEntity
import com.defaultxyz.skyline.domain.db.entity.UserEntity

@Database(entities = [UserEntity::class, LocationEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun locationDao(): LocationDao

    companion object {

        @Volatile
        @JvmStatic
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "db_skyline.db"
                        ).build()
                    }
                }
            }
            return requireNotNull(INSTANCE) { "Database instance cannot be null" }
        }
    }

}