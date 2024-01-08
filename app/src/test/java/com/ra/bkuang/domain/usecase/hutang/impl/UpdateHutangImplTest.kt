package com.ra.bkuang.domain.usecase.hutang.impl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.repository.HutangRepository
import com.ra.bkuang.domain.usecase.hutang.UpdateHutang
import com.ra.bkuang.dummy.model.HutangDummy
import com.ra.bkuang.domain.util.ResourceState
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UpdateHutangImplTest {
  private val hutangRepository: HutangRepository = mock()
  private lateinit var updateHutang: UpdateHutang

  @Test
  fun `UpdateHutang, should success`() = runBlocking {
    updateHutang = UpdateHutangImpl(hutangRepository)

    val actualHutang= HutangDummy.getAllHutang()[0]

    whenever(hutangRepository.update(actualHutang))
      .thenReturn(Unit)

    updateHutang.invoke(actualHutang.toModel()).test {
      val expectedVal1 = awaitItem()
      val expectedVal2 = awaitItem()
      assertEquals(expectedVal1, ResourceState.LOADING)
      assertEquals(expectedVal2, ResourceState.SUCCESS)
      awaitComplete()
    }
  }
}