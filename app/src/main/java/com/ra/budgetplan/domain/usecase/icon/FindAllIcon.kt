package com.ra.budgetplan.domain.usecase.icon

import com.ra.budgetplan.domain.model.IconModel
import kotlinx.coroutines.flow.Flow

interface FindAllIcon {
  fun invoke(): Flow<List<IconModel>>
}