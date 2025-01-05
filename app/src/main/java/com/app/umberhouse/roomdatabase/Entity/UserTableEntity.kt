package com.app.umberhouse.roomdatabase.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Login")
class UserTableEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var username: String,
    var password: String
)