package com.example.araccessories.data.dataSource.localDataSource.room.cacheDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.araccessories.data.dataSource.localDataSource.entities.Products
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote

@Database(entities = [ProductsRemote::class], version = 16, exportSchema = false)
abstract class ProductsOfflineDatabase : RoomDatabase() {
    abstract val productDao :ProductsDao
    companion object {

        @Volatile
        private var INSTANCE: ProductsOfflineDatabase? = null

        fun getInstance(context: Context): ProductsOfflineDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProductsOfflineDatabase::class.java,
                        "products_database"
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