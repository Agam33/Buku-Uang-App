package com.ra.budgetplan.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.ra.budgetplan.domain.entity.PengeluaranEntity

@Dao
interface PengeluaranDao {
  @Insert
  fun save(pengeluaran: PengeluaranEntity)

  @Delete
  fun delete(pengeluaran: PengeluaranEntity)

  @Update
  fun update(pengeluaran: PengeluaranEntity)
}