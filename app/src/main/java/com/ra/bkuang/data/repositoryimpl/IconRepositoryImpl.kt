package com.ra.bkuang.data.repositoryimpl

import com.ra.bkuang.customview.dialog.icon.IconCategory
import com.ra.bkuang.data.local.IconLocalDataSource
import com.ra.bkuang.domain.entity.IconEntity
import com.ra.bkuang.domain.repository.IconRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IconRepositoryImpl @Inject constructor(
  private val iconLocalDataSource: IconLocalDataSource
): IconRepository {
  override fun findAll(): Flow<List<IconEntity>> {
    return iconLocalDataSource.findAll()
  }

  override fun findByCategory(iconCategory: IconCategory): Flow<List<IconEntity>> {
    return iconLocalDataSource.findByCategory(iconCategory)
  }
}