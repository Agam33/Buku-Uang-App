package com.ra.budgetplan.data.local.datasourceimpl

import com.ra.budgetplan.customview.dialog.icon.IconCategory
import com.ra.budgetplan.data.local.IconLocalDataSource
import com.ra.budgetplan.data.local.database.dao.IconDao
import com.ra.budgetplan.domain.entity.IconEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IconLocalDataSourceImpl @Inject constructor(
  private val iconDao: IconDao
): IconLocalDataSource {
  override fun findAll(): Flow<List<IconEntity>> {
    return iconDao.findAll()
  }

  override fun findByCategory(iconCategory: IconCategory): Flow<List<IconEntity>> {
    return iconDao.findByCategory(iconCategory)
  }
}