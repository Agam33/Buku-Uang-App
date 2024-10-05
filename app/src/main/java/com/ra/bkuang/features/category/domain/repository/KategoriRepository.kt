package com.ra.bkuang.features.category.domain.repository

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.category.data.model.KategoriModel
import com.ra.bkuang.core.data.source.local.database.entity.TransactionType
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface KategoriRepository {
  fun findByType(type: TransactionType): Flow<List<KategoriModel>>
  fun save(kategori: KategoriModel): Flow<Result<Boolean>>
  fun delete(kategori: KategoriModel): Flow<Result<Boolean>>
  fun update(kategori: KategoriModel): Flow<Result<Boolean>>
  fun findAll(): Flow<List<KategoriModel>>
  fun findById(id: UUID): Flow<KategoriModel>
}