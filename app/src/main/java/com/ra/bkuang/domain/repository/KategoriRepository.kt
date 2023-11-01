package com.ra.bkuang.domain.repository

import com.ra.bkuang.data.entity.KategoriEntity
import com.ra.bkuang.data.entity.TipeKategori
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface KategoriRepository {
  suspend fun findByType(type: TipeKategori): Flow<List<KategoriEntity>>
  suspend fun save(kategori: KategoriEntity)
  suspend fun delete(kategori: KategoriEntity)
  suspend fun update(kategori: KategoriEntity)
  fun findAll(): Flow<List<KategoriEntity>>
  suspend fun findById(id: UUID): Flow<KategoriEntity>
}