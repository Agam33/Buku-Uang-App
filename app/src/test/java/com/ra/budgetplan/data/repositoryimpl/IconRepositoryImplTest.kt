package com.ra.budgetplan.data.repositoryimpl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.customview.dialog.icon.IconCategory
import com.ra.budgetplan.data.local.IconLocalDataSource
import com.ra.budgetplan.domain.repository.IconRepository
import com.ra.budgetplan.dummy.model.IconDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class IconRepositoryImplTest {
  private lateinit var iconRepository: IconRepository
  private val iconLocalDataSource: IconLocalDataSource = mock()

  @Test
  fun `FindAll, should success`() = runBlocking {
    iconRepository = IconRepositoryImpl(iconLocalDataSource)

    val actualListIcon = IconDummy.getAllIcon()
    val flow = flow { emit(actualListIcon) }

    whenever(iconRepository.findAll())
      .thenReturn(flow)

    iconRepository.findAll().test {
      val expectedVal = awaitItem()
      assertEquals(actualListIcon, expectedVal)
      assertEquals(actualListIcon.size, expectedVal.size)
      awaitComplete()
    }
  }

  @Test
  fun `FindByCategory, should success`() = runBlocking {
    iconRepository = IconRepositoryImpl(iconLocalDataSource)

    val actualListIcon = IconDummy.getAllIcon()
    val flow = flow { emit(actualListIcon) }

    whenever(iconRepository.findByCategory(IconCategory.INCOME))
      .thenReturn(flow)

    iconRepository.findByCategory(IconCategory.INCOME).test {
      val expectedVal = awaitItem()
      assertEquals(actualListIcon, expectedVal)
      assertEquals(actualListIcon.size, expectedVal.size)
      awaitComplete()
    }
  }
}