package com.ra.bkuang.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ra.bkuang.customview.dialog.icon.IconCategory
import com.ra.bkuang.data.entity.IconEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IconDao {

  @Query("SELECT * FROM IconEntity")
  fun findAll(): Flow<List<IconEntity>>

  @Query("SELECT * FROM IconEntity as ic WHERE ic.category = :iconCategory")
  fun findByCategory(iconCategory: IconCategory): Flow<List<IconEntity>>

  @Insert
  suspend fun addAll(icons: List<IconEntity>)
}