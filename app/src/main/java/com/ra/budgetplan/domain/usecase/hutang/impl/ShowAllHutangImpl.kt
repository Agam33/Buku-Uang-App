package com.ra.budgetplan.domain.usecase.hutang.impl

import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.HutangModel
import com.ra.budgetplan.domain.repository.HutangRepository
import com.ra.budgetplan.domain.usecase.hutang.ShowAllHutang
import com.ra.budgetplan.util.Resource
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