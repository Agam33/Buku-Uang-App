package com.ra.bkuang.domain.usecase.transaksi.pendapatan.impl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.repository.PendapatanRepository
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.GetTotalPendapatan
import com.ra.bkuang.dummy.model.PendapatanDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GetTotalPendapatanImplTest {
  private val repository: PendapatanRepository = mock()
  private lateinit var getTotalPendapatan: GetTotalPendapatan

  @Test
  fun `GetTotalPendapatan, should success`() = runBlocking {
    getTotalPendapatan = GetTotalPendapatanImpl(repository)

    val actualTotalPendapatan = PendapatanDummy.getTotalPendapatan()

    whenever(repository.getTotalPendapatan()).thenReturn(flow { emit(actualTotalPendapatan) })

    getTotalPendapatan.invoke().test {
      val expectedVal = awaitItem()
      assertEquals(expectedVal, actualTotalPendapatan)
      awaitComplete()
    }
  }
}