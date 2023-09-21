package com.ra.budgetplan.domain.usecase.akun.impl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.usecase.akun.AkunOverallMoney
import com.ra.budgetplan.dummy.model.AkunDummy
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AkunOverallMoneyImplTest {
  private lateinit var akunOverallMoney: AkunOverallMoney
  private val repository: AkunRepository = mock()

  @Test
  fun `GetOverallMoney, should success`() = runBlocking {
    akunOverallMoney = AkunOverallMoneyImpl(repository)

    val actualTotalMoney: Long = AkunDummy.getTotalMoney()
    val flow = flow { emit(actualTotalMoney) }

    whenever(repository.getTotalMoney())
      .thenReturn(flow)

    akunOverallMoney.invoke().test {
      val expectedItem = awaitItem()
      assertEquals(actualTotalMoney, expectedItem)
      awaitComplete()
    }
  }
}