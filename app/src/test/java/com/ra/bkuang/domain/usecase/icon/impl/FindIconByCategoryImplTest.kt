package com.ra.bkuang.domain.usecase.icon.impl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.presentation.ui.customview.dialog.icon.IconCategory
import com.ra.bkuang.domain.usecase.icon.FindIconByCategory
import com.ra.bkuang.dummy.model.IconDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class FindIconByCategoryImplTest {

  private lateinit var findIconByCategory: FindIconByCategory
  private val iconRepository: IconRepository = mock()

  @Test
  fun `FindIconByCategori, should success`() = runBlocking {
    findIconByCategory = FindIconByCategoryImpl(iconRepository)

    val actualIcon = IconDummy.getAllIcon()

    whenever(iconRepository.findByCategory(IconCategory.INCOME))
      .thenReturn(flow { emit(actualIcon) })

    findIconByCategory.invoke(IconCategory.INCOME).test {
      val expectedItem = awaitItem()
      assertEquals(expectedItem.size, actualIcon.size)
      awaitComplete()
    }
  }
}