package com.ra.budgetplan.domain.usecase.icon.impl

import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.IconModel
import com.ra.budgetplan.domain.repository.IconRepository
import com.ra.budgetplan.domain.usecase.icon.FindAllIcon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FindAllIconImpl @Inject constructor(
  private val repository: IconRepository
): FindAllIcon {
  override fun invoke(): Flow<List<IconModel>> {
    return flow {
      repository.findAll().collect {list ->
        val result = list.map {
          it.toModel()
        }
        emit(result)
      }
    }
  }
}