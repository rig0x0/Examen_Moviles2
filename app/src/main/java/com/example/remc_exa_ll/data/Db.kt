package com.example.remc_exa_ll.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.remc_exa_ll.entity.CommentEntity
import com.example.remc_exa_ll.entity.PostEntity
import com.example.remc_exa_ll.entity.UserEntity

@Database(entities = [UserEntity::class, PostEntity::class, CommentEntity::class], version = 2)
abstract class Db: RoomDatabase() {
    abstract fun userDAO(): UserDAO
    abstract fun postDAO(): PostDAO
    abstract fun commentDAO(): CommentDAO

    companion object {
        @Volatile
        private var INSTANCE: Db? = null

        fun getDatabase(context: Context): Db {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Db::class.java,
                    "mascota_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }

}