package com.ra.budgetplan.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface TransferDao {
  @Insert
  fun save()

  @Delete
  fun delete()

  @Update
  fun update()
}