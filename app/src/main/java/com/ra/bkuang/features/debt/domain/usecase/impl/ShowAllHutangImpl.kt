package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.common.util.Resource
import com.ra.bkuang.features.debt.data.mapper.toModel
import com.ra.bkuang.features.debt.domain.HutangRepository
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.ShowAllHutang
import javax.inject.Inject

class ShowAllHutangImpl @Inject constructor(
  private val hutangRepository: HutangRepository
): ShowAllHutang {
  override suspend fun invoke(): Resource<List<HutangModel>> {
    val list =  hutangRepository.findAll().map { it.toModel() }
    if(list.isEmpty()) {
      return Resource.Empty("")
    }
    return Resource.Success(list)
  }
}