package com.ra.budgetplan.domain.usecase.hutang.impl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.repository.HutangRepository
import com.ra.budgetplan.domain.usecase.hutang.DeleteHutang
import com.ra.budgetplan.dummy.model.HutangDummy
import com.ra.budgetplan.util.StatusItem
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DeleteHutangImplTest {
  private val hutangRepository: HutangRepository = mock()
  private lateinit var deleteHutang: DeleteHutang

  @Test
  fun `DeleteHutang, should be success`() = runBlocking {
    deleteHutang = DeleteHutangImpl(hutangRepository)

    val actualHutang = HutangDummy.getAllHutang()[0]

    whenever(hutangRepository.delete(actualHutang))
      .thenReturn(Unit)

    deleteHutang.invoke(actualHutang.toModel()).test {
      val expectedVal1 = awaitItem()
      val expectedVal2 = awaitItem()
      assertEquals(expectedVal1, StatusItem.LOADING)
      assertEquals(expectedVal2, StatusItem.SUCCESS)
      awaitComplete()
    }
  }
}