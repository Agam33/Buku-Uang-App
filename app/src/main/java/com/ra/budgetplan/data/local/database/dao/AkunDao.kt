package com.ra.budgetplan.data.local.database.dao

import androidx.room.*
import com.ra.budgetplan.domain.entity.AkunEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface AkunDao {

  @Insert
  suspend fun save(akunEntity: AkunEntity)

  @Update
  suspend fun update(akunEntity: AkunEntity)

  @Delete
  suspend fun delete(akunEntity: AkunEntity)

  @Query("SELECT * FROM akunentity ORDER BY updated_at ASC")
  fun findAll(): Flow<List<AkunEntity>>

  @Query("SELECT * FROM akunentity AS akun WHERE akun.uuid = :id")
  fun findById(id: UUID): Flow<AkunEntity>

}