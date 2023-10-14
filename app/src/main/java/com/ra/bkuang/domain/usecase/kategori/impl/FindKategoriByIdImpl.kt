package com.ra.bkuang.domain.usecase.kategori.impl

import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.KategoriModel
import com.ra.bkuang.domain.repository.KategoriRepository
import com.ra.bkuang.domain.usecase.kategori.FindKategoriById
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class FindKategoriByIdImpl @Inject constructor(
  private val repository: KategoriRepository
): FindKategoriById {

  override suspend fun invoke(id: UUID): Flow<KategoriModel> {
    return flow {
      repository.findById(id).collect {
        emit(it.toModel())
      }
    }
  }
}