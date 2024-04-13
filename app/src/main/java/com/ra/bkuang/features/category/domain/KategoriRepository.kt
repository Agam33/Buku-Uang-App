package com.ra.bkuang.features.category.domain

import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface KategoriRepository {
  suspend fun findByType(type: TransactionType): Flow<List<KategoriModel>>
  suspend fun save(kategori: KategoriModel)
  suspend fun delete(kategori: KategoriModel)
  suspend fun update(kategori: KategoriModel)
  fun findAll(): Flow<List<KategoriModel>>
  suspend fun findById(id: UUID): Flow<KategoriModel>
}