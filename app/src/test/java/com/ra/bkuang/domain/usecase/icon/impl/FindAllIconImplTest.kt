package com.ra.bkuang.domain.usecase.icon.impl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.repository.IconRepository
import com.ra.bkuang.domain.usecase.icon.FindAllIcon
import com.ra.bkuang.dummy.model.IconDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class FindAllIconImplTest {
  private lateinit var findAllIcon: FindAllIcon
  private val iconRepository: IconRepository = mock()

  @Test
  fun `FindAllIcon, should success`() = runBlocking {
    findAllIcon = FindAllIconImpl(iconRepository)

    val actualListIcon = IconDummy.getAllIcon()

    whenever(iconRepository.findAll())
      .thenReturn(flow { emit(actualListIcon) })

    findAllIcon.invoke().test {
      val expectedVal = awaitItem()
      Assertions.assertEquals(actualListIcon.size, expectedVal.size)
      awaitComplete()
    }
  }
}