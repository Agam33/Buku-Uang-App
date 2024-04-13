package com.ra.bkuang.features.category.data.local

import com.ra.bkuang.features.category.data.entity.KategoriEntity
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface KategoriLocalDataSource {
  suspend fun findByType(type: TransactionType): Flow<List<KategoriEntity>>
  suspend fun saveKategori(kategori: KategoriEntity)
  suspend fun deleteKategori(kategori: KategoriEntity)
  suspend fun updateKategori(kategori: KategoriEntity)
  fun findAllKategori(): Flow<List<KategoriEntity>>
  suspend fun findKategoriById(id: UUID): Flow<KategoriEntity>
}