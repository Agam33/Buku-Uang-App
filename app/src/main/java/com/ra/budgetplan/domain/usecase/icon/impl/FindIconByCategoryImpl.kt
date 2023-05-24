package com.ra.budgetplan.domain.usecase.icon.impl

import com.ra.budgetplan.customview.dialog.icon.IconCategory
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.IconModel
import com.ra.budgetplan.domain.repository.IconRepository
import com.ra.budgetplan.domain.usecase.icon.FindIconByCategory
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