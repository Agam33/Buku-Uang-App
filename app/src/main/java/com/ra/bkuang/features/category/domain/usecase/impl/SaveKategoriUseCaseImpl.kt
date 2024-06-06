package com.ra.bkuang.features.category.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.category.domain.KategoriRepository
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.category.domain.usecase.SaveKategoriUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveKategoriUseCaseImpl @Inject constructor(
  private val repository: KategoriRepository
): SaveKategoriUseCase {
  override fun invoke(kategori: KategoriModel): Flow<Result<Boolean>> {
    return repository.save(kategori)
  }
}