package com.example.araccessories.data.dataSource.localDataSource.room.cacheDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.araccessories.data.dataSource.localDataSource.entities.Products
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductsRemote>)


    @Query("SELECT * FROM  ProductsRemote ")
    suspend fun getAllProducts(): List<ProductsRemote>?


}