package com.ra.bkuang.core.data.source.local.database.dao

import androidx.room.*
import com.ra.bkuang.core.data.source.local.database.entity.KategoriEntity
import com.ra.bkuang.core.data.source.local.database.entity.TransactionType
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface KategoriDao {

  @Query("SELECT * FROM kategorientity AS kt WHERE kt.tipe_kategori = :type")
  fun findByType(type: TransactionType): Flow<List<KategoriEntity>>

  @Insert
  suspend fun saveAll(kategori: List<KategoriEntity>)

  @Insert
  suspend fun save(kategori: KategoriEntity)

  @Delete
  suspend fun delete(kategori: KategoriEntity)

  @Update
  suspend fun update(kategori: KategoriEntity)

  @Query("SELECT * FROM kategorientity ORDER BY created_at")
  fun findAll(): Flow<List<KategoriEntity>>

  @Query("SELECT * FROM kategorientity AS kt WHERE kt.uuid = :id")
  fun findById(id: UUID): Flow<KategoriEntity>
}