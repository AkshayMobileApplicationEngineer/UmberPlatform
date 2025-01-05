package com.app.umberhouse.roomdatabase.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.umberhouse.roomdatabase.Entity.UserTableEntity

@Dao
interface CustomerDao {
    @Insert
    suspend fun insertCustomer(customer: UserTableEntity)


}