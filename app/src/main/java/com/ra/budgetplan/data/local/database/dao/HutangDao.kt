package com.ra.budgetplan.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.ra.budgetplan.domain.entity.HutangEntity


@Dao
interface HutangDao {
  @Insert
  suspend fun save(hutang: HutangEntity)

  @Delete
  suspend fun delete(hutang: HutangEntity)

  @Update
  suspend fun update(hutang: HutangEntity)
}