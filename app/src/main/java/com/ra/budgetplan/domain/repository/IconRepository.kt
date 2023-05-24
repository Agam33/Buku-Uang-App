package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.customview.dialog.icon.IconCategory
import com.ra.budgetplan.domain.entity.IconEntity
import kotlinx.coroutines.flow.Flow

interface IconRepository {
  fun findAll(): Flow<List<IconEntity>>
  fun findByCategory(iconCategory: IconCategory): Flow<List<IconEntity>>
}