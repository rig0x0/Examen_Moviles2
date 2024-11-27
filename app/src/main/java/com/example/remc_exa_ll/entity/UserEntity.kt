package com.example.remc_exa_ll.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Embedded

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val username: String,
    val email: String,
    @Embedded val address: Address,
    @Embedded val company: Company
)

data class Address(
    val city: String,
    val street: String,
    val suite: String,
    val zipcode: String,
    @Embedded val geo: Geo   // @Embedded para almacenar la información geográfica como un objeto dentro de Address
)

data class Geo(
    val lat: String,
    val lng: String
)

data class Company(
    @ColumnInfo(name = "company_name")
    val name: String,
    val catchPhrase: String,
    val bs: String
)