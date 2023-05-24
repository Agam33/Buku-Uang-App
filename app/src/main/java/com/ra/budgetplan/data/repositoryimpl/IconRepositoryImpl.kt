package com.ra.budgetplan.data.repositoryimpl

import com.ra.budgetplan.customview.dialog.icon.IconCategory
import com.ra.budgetplan.data.local.IconLocalDataSource
import com.ra.budgetplan.domain.entity.IconEntity
import com.ra.budgetplan.domain.repository.IconRepository
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