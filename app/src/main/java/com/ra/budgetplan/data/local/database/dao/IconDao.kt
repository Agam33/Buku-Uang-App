package com.ra.budgetplan.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ra.budgetplan.customview.dialog.icon.IconCategory
import com.ra.budgetplan.domain.entity.IconEntity
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