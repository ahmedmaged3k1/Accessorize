package com.example.araccessories.data.dataSource.localDataSource.room.userDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.data.dataSource.remoteDataSource.entities.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)


    @Query("SELECT * FROM  User where email =  :name  ")
    suspend fun getUserByEmail(name: String): User?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(user: User)

}