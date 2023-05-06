package com.ra.budgetplan.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.ra.budgetplan.domain.entity.PendapatanEntity

@Dao
interface PendapatanDao {
  @Insert
  fun save(pendapatan: PendapatanEntity)

  @Delete
  fun delete(pendapatan: PendapatanEntity)

  @Update
  fun update(pendapatan: PendapatanEntity)
}