package com.ra.bkuang.domain.usecase.kategori.impl

import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.model.KategoriModel
import com.ra.bkuang.domain.repository.KategoriRepository
import com.ra.bkuang.domain.usecase.kategori.DeleteKategori
import com.ra.bkuang.domain.util.ResourceState
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