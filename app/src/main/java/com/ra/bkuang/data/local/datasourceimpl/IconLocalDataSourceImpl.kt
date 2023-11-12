package com.ra.bkuang.data.local.datasourceimpl

import com.ra.bkuang.customview.dialog.icon.IconCategory
import com.ra.bkuang.data.local.IconLocalDataSource
import com.ra.bkuang.data.local.database.dao.IconDao
import com.ra.bkuang.data.local.entity.IconEntity
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