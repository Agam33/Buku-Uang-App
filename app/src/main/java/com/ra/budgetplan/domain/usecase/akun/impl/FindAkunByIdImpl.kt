package com.ra.budgetplan.domain.usecase.akun.impl

import com.ra.budgetplan.domain.mapper.AkunMapper
import com.ra.budgetplan.domain.model.AkunModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.usecase.akun.FindAkunById
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class FindAkunByIdImpl @Inject constructor(
  private val repository: AkunRepository
): FindAkunById {

  override fun invoke(id: UUID): Flow<AkunModel> {
      return repository.findById(id).map {
        AkunMapper.akunToModel(it)
      }
  }
}