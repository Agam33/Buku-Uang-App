package com.ra.bkuang.domain.usecase.hutang.impl

import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.HutangModel
import com.ra.bkuang.domain.repository.HutangRepository
import com.ra.bkuang.domain.usecase.hutang.FindHutangById
import java.util.UUID
import javax.inject.Inject

class FindHutangByIdImpl @Inject constructor(
  private val hutangRepository: HutangRepository
): FindHutangById {
  override suspend fun invoke(id: UUID): HutangModel {
    return hutangRepository.findById(id).toModel()
  }
}