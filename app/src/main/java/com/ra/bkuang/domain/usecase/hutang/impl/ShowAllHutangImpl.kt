package com.ra.bkuang.domain.usecase.hutang.impl

import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.HutangModel
import com.ra.bkuang.domain.repository.HutangRepository
import com.ra.bkuang.domain.usecase.hutang.ShowAllHutang
import com.ra.bkuang.domain.util.Resource
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