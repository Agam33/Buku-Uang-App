package com.ra.bkuang.features.category.domain.usecase.impl

import com.ra.bkuang.features.category.domain.KategoriRepository
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.category.domain.usecase.FindKategoriByIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class FindKategoriByIdUseCaseImpl @Inject constructor(private val repository: KategoriRepository
): FindKategoriByIdUseCase {

  override operator fun invoke(id: UUID): Flow<KategoriModel> {
    return flow {
      repository.findById(id).collect {
        emit(it)
      }
    }
  }
}