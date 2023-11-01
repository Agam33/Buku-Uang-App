package com.ra.bkuang.data.local.database.dao

import androidx.room.*
import com.ra.bkuang.data.entity.KategoriEntity
import com.ra.bkuang.data.entity.TipeKategori
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface KategoriDao {

  @Query("SELECT * FROM kategorientity AS kt WHERE kt.tipe_kategori = :type")
  fun findByType(type: TipeKategori): Flow<List<KategoriEntity>>

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