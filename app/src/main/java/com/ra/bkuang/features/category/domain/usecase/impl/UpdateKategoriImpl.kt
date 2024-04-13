package com.ra.bkuang.features.category.domain.usecase.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.category.data.mapper.toEntity
import com.ra.bkuang.features.category.domain.KategoriRepository
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.category.domain.usecase.UpdateKategori
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateKategoriImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val repository: KategoriRepository
): UpdateKategori {
  override suspend fun invoke(kategori: KategoriModel) = withContext(ioDispatcher) {
    return@withContext repository.update(kategori)
  }
}