package com.ra.budgetplan.data.local

import com.ra.budgetplan.customview.dialog.icon.IconCategory
import com.ra.budgetplan.domain.entity.IconEntity
import kotlinx.coroutines.flow.Flow

interface IconLocalDataSource {
  fun findAll(): Flow<List<IconEntity>>
  fun findByCategory(iconCategory: IconCategory): Flow<List<IconEntity>>
}