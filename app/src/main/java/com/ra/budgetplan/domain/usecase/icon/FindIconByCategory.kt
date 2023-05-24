package com.ra.budgetplan.domain.usecase.icon

import com.ra.budgetplan.customview.dialog.icon.IconCategory
import com.ra.budgetplan.domain.model.IconModel
import kotlinx.coroutines.flow.Flow

interface FindIconByCategory {
  fun invoke(iconCategory: IconCategory): Flow<List<IconModel>>
}