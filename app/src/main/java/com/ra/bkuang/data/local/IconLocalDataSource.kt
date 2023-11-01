package com.ra.bkuang.data.local

import com.ra.bkuang.customview.dialog.icon.IconCategory
import com.ra.bkuang.data.entity.IconEntity
import kotlinx.coroutines.flow.Flow

interface IconLocalDataSource {
  fun findAll(): Flow<List<IconEntity>>
  fun findByCategory(iconCategory: IconCategory): Flow<List<IconEntity>>
}