package com.ra.bkuang.domain.usecase.icon.impl

import com.ra.bkuang.customview.dialog.icon.IconCategory
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.IconModel
import com.ra.bkuang.domain.repository.IconRepository
import com.ra.bkuang.domain.usecase.icon.FindIconByCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FindIconByCategoryImpl @Inject constructor(
  private val iconRepository: IconRepository
): FindIconByCategory {

  override fun invoke(iconCategory: IconCategory): Flow<List<IconModel>> {
    return flow {
      iconRepository.findByCategory(iconCategory).collect {
        val result = it.map { entity ->
          entity.toModel()
        }
        emit(result)
      }
    }
  }
}