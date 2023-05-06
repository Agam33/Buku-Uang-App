package com.ra.budgetplan.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.ra.budgetplan.domain.entity.TabunganEntity

@Dao
interface TabunganDao {

  @Insert
  fun save(tabungan: TabunganEntity)

  @Update
  fun update(tabungan: TabunganEntity)

  @Delete
  fun delete(tabungan: TabunganEntity)


}