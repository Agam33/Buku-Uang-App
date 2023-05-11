package com.ra.budgetplan.data.local

import androidx.lifecycle.LiveData
import com.ra.budgetplan.domain.entity.KategoriEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface KategoriLocalDataSource {
  suspend fun saveKategori(kategori: KategoriEntity)
  suspend fun deleteKategori(kategori: KategoriEntity)
  suspend fun updateKategori(kategori: KategoriEntity)
  fun findAllKategori(): Flow<List<KategoriEntity>>
  fun findKategoriById(id: UUID): Flow<KategoriEntity>
}