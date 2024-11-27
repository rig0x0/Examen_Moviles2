package com.example.remc_exa_ll.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
data class PostEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
)
