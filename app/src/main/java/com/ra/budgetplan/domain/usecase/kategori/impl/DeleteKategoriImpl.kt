package com.ra.budgetplan.domain.usecase.kategori.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.model.KategoriModel
import com.ra.budgetplan.domain.repository.KategoriRepository
import com.ra.budgetplan.domain.usecase.kategori.DeleteKategori
import com.ra.budgetplan.util.StatusItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteKategoriImpl @Inject constructor(
  private val repository: KategoriRepository
): DeleteKategori {
  override suspend fun invoke(kategori: KategoriModel): Flow<StatusItem> {
    return flow {
      emit(StatusItem.LOADING)
      repository.delete(kategori.toEntity())
      emit(StatusItem.SUCCESS)
    }
  }
}