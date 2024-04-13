package com.ra.bkuang.features.category.domain.usecase.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.category.data.mapper.toEntity
import com.ra.bkuang.features.category.domain.KategoriRepository
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.category.domain.usecase.DeleteKategori
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteKategoriImpl @Inject constructor(
  private val repository: KategoriRepository
): DeleteKategori {
  override suspend fun invoke(kategori: KategoriModel): Flow<ResourceState> {
    return flow {
      emit(ResourceState.LOADING)
      repository.delete(kategori.toEntity())
      emit(ResourceState.SUCCESS)
    }
  }
}