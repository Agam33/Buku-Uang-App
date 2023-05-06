package com.ra.budgetplan.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.ra.budgetplan.domain.entity.KategoriEntity

@Dao
interface KategoriDao {

  @Insert
  fun save(kategori: KategoriEntity)

  @Delete
  fun delete(kategori: KategoriEntity)

  @Update
  fun update(kategori: KategoriEntity)
}