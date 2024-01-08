package com.ra.bkuang.data.local.datasource

import com.ra.bkuang.presentation.ui.customview.dialog.icon.IconCategory
import com.ra.bkuang.data.local.entity.IconEntity
import kotlinx.coroutines.flow.Flow

interface IconLocalDataSource {
  fun findAll(): Flow<List<IconEntity>>
  fun findByCategory(iconCategory: IconCategory): Flow<List<IconEntity>>
}