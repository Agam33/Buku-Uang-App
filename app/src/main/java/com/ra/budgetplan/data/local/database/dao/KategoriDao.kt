package com.ra.budgetplan.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ra.budgetplan.domain.entity.KategoriEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface KategoriDao {

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