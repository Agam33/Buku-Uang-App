package com.ra.budgetplan.domain.usecase.hutang.impl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.repository.HutangRepository
import com.ra.budgetplan.domain.usecase.hutang.FindHutangByIdWithFlow
import com.ra.budgetplan.dummy.model.HutangDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class FindHutangByIdWithFlowImplTest {
  private val hutangRepository: HutangRepository = mock()
  private lateinit var findHutangByIdWithFlow: FindHutangByIdWithFlow

  @Test
  fun `FindHutangByIdWithFlow, should be sucess`() = runBlocking {
    findHutangByIdWithFlow= FindHutangByIdWithFlowImpl(hutangRepository)

    val actualHutang = HutangDummy.getAllHutang()[0]

    whenever(hutangRepository.findByIdWithFlow(actualHutang.uuid))
      .thenReturn(flow { emit(actualHutang) })

    findHutangByIdWithFlow.invoke(actualHutang.uuid).test {
      val expectedVal = awaitItem()
      assertEquals(actualHutang.toModel(), expectedVal)
      assertEquals(actualHutang.toModel().uuid, expectedVal.uuid)
      awaitComplete()
    }
  }
}