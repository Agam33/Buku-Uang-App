package com.ra.bkuang.domain.usecase.icon.impl

import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.IconModel
import com.ra.bkuang.domain.repository.IconRepository
import com.ra.bkuang.domain.usecase.icon.FindAllIcon
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