package com.ra.bkuang.domain.repository

import com.ra.bkuang.customview.dialog.icon.IconCategory
import com.ra.bkuang.domain.entity.IconEntity
import kotlinx.coroutines.flow.Flow

interface IconRepository {
  fun findAll(): Flow<List<IconEntity>>
  fun findByCategory(iconCategory: IconCategory): Flow<List<IconEntity>>
}