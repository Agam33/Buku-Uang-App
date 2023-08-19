package com.ra.budgetplan.data.local.datasourceimpl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.customview.dialog.icon.IconCategory
import com.ra.budgetplan.data.local.IconLocalDataSource
import com.ra.budgetplan.data.local.database.dao.IconDao
import com.ra.budgetplan.dummy.model.IconDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class IconLocalDataSourceImplTest {
  private lateinit var iconLocalDataSource: IconLocalDataSource
  private val iconDao: IconDao = mock()

  @Test
  fun `FindAll, should success`() = runBlocking {
    iconLocalDataSource = IconLocalDataSourceImpl(iconDao)

    val actualListIcon = IconDummy.getAllIcon()
    val flow = flow { emit(actualListIcon) }

    whenever(iconLocalDataSource.findAll()).thenReturn(flow)

    iconLocalDataSource.findAll().test {
      val expectedVal = expectItem()
      Assertions.assertEquals(expectedVal, actualListIcon)
      Assertions.assertEquals(expectedVal.size, actualListIcon.size)
      expectComplete()
    }
  }

  @Test
  fun `FindByCategory, should success`() = runBlocking {
    iconLocalDataSource = IconLocalDataSourceImpl(iconDao)

    val actualListIcon = IconDummy.getAllIcon()
    val flow = flow { emit(actualListIcon) }

    whenever(iconLocalDataSource.findByCategory(IconCategory.EXPENSE))
      .thenReturn(flow)

    iconLocalDataSource.findByCategory(IconCategory.EXPENSE).test {
      val expectedItem = expectItem()
      Assertions.assertEquals(actualListIcon.size, expectedItem.size)
      Assertions.assertEquals(actualListIcon, expectedItem)
      expectComplete()
    }
  }
}