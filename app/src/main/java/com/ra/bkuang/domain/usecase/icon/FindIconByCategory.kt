package com.ra.bkuang.domain.usecase.icon

import com.ra.bkuang.presentation.ui.customview.dialog.icon.IconCategory
import com.ra.bkuang.domain.model.IconModel
import kotlinx.coroutines.flow.Flow

interface FindIconByCategory {
  fun invoke(iconCategory: IconCategory): Flow<List<IconModel>>
}