package com.ra.bkuang.data.local

import com.ra.bkuang.domain.entity.KategoriEntity
import com.ra.bkuang.domain.entity.TipeKategori
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface KategoriLocalDataSource {
  suspend fun findByType(type: TipeKategori): Flow<List<KategoriEntity>>
  suspend fun saveKategori(kategori: KategoriEntity)
  suspend fun deleteKategori(kategori: KategoriEntity)
  suspend fun updateKategori(kategori: KategoriEntity)
  fun findAllKategori(): Flow<List<KategoriEntity>>
  suspend fun findKategoriById(id: UUID): Flow<KategoriEntity>
}