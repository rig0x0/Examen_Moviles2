package com.example.remc_exa_ll.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.remc_exa_ll.entity.UserEntity

@Dao
interface UserDAO {
    @Insert
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getUserById(userId: Int): UserEntity?

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<UserEntity>

}