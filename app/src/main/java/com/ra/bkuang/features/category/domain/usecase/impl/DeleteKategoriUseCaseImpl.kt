package com.ra.bkuang.features.category.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.category.domain.repository.KategoriRepository
import com.ra.bkuang.features.category.data.model.KategoriModel
import com.ra.bkuang.features.category.domain.usecase.DeleteKategoriUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteKategoriUseCaseImpl @Inject constructor(
  private val repository: KategoriRepository
): DeleteKategoriUseCase {
  override operator fun invoke(kategori: KategoriModel): Flow<Result<Boolean>> {
    return repository.delete(kategori)
  }
}