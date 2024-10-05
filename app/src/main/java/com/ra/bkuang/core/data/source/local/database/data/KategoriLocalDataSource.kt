package com.ra.bkuang.core.data.source.local.database.data

import com.ra.bkuang.core.data.source.local.database.entity.KategoriEntity
import com.ra.bkuang.core.data.source.local.database.entity.TransactionType
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface KategoriLocalDataSource {
  fun findByType(type: TransactionType): Flow<List<KategoriEntity>>
  suspend fun saveKategori(kategori: KategoriEntity)
  suspend fun deleteKategori(kategori: KategoriEntity)
  suspend fun updateKategori(kategori: KategoriEntity)
  fun findAllKategori(): Flow<List<KategoriEntity>>
  fun findKategoriById(id: UUID): Flow<KategoriEntity>
}