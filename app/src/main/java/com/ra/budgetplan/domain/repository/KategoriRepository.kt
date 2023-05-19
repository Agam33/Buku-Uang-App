package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.domain.model.KategoriModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface KategoriRepository {
  suspend fun save(kategori: KategoriModel)
  suspend fun delete(kategori: KategoriModel)
  suspend fun update(kategori: KategoriModel)
  fun findAll(): Flow<List<KategoriModel>>
  fun findById(id: UUID): Flow<KategoriModel>
}