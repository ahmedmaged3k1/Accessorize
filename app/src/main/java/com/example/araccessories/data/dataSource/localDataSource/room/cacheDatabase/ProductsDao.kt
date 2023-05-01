package com.example.araccessories.data.dataSource.localDataSource.room.cacheDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.araccessories.data.dataSource.localDataSource.entities.Products
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.data.dataSource.remoteDataSource.entities.User

@Dao
interface ProductsDao {
  /*  @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: ProductsRemote)*/


  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insertAllProducts(productsRemoteList: List<ProductsRemote>)
  @Insert
  suspend fun insertProduct( productsRemote: ProductsRemote)

  @Delete
  suspend fun delete(productsRemote: ProductsRemote)

  @Update
  suspend fun update(productsRemote: ProductsRemote)
    @Query("SELECT * FROM  ProductsRemote where userEmail =  :name  ")
    suspend fun getProductsByEmail(name: String): List<ProductsRemote>?

    @Query("SELECT * FROM  ProductsRemote ")
    suspend fun getAllProducts(): List<ProductsRemote>?







}