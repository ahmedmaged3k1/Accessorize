package com.example.araccessories.data.dataSource.localDataSource.room.userDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.araccessories.data.dataSource.remoteDataSource.entities.User

@Database(entities = [User::class], version = 3, exportSchema = false)

abstract class UserDatabase : RoomDatabase() {
    abstract val userDao : UserDao
    companion object {

        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}