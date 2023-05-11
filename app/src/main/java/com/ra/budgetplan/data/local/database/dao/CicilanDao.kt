package com.ra.budgetplan.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ra.budgetplan.domain.entity.CicilanEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface CicilanDao {

  @Insert
  suspend fun save(cicilan: CicilanEntity)

  @Delete
  suspend fun delete(cicilan: CicilanEntity)

  @Update
  suspend fun update(cicilan: CicilanEntity)

  @Query("SELECT * FROM cicilan_tbl AS ccl WHERE ccl.uuid =:id")
  fun findById(id: UUID): Flow<CicilanEntity>

  @Query("SELECT * FROM cicilan_tbl")
  fun findAll(): Flow<List<CicilanEntity>>
}