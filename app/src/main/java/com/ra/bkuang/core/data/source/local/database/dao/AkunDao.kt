package com.ra.bkuang.core.data.source.local.database.dao

import androidx.room.*
import com.ra.bkuang.core.data.source.local.database.entity.AkunEntity
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

  @Query("SELECT * FROM akunentity ORDER BY updated_at DESC")
  fun findAllWithFlow(): Flow<List<AkunEntity>>

  @Query("SELECT * FROM akunentity ORDER BY updated_at DESC")
  fun findAll(): List<AkunEntity>

  @Query("SELECT * FROM akunentity AS akun WHERE akun.uuid = :id")
  suspend fun findById(id: UUID): AkunEntity

  @Query("SELECT SUM(total) FROM akunentity")
  fun getTotalMoney(): Flow<Long?>

}