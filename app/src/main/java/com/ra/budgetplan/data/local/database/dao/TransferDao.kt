package com.ra.budgetplan.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.ra.budgetplan.domain.entity.TransferEntity

@Dao
interface TransferDao {
  @Insert
  suspend fun save(transferEntity: TransferEntity)

  @Delete
  suspend fun delete(transferEntity: TransferEntity)

  @Update
  suspend fun update(transferEntity: TransferEntity)
}