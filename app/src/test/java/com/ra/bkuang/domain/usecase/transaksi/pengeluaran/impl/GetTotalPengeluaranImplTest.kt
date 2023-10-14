package com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.repository.PengeluaranRepository
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.GetTotalPengeluaran
import com.ra.bkuang.dummy.model.PengeluaranDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GetTotalPengeluaranImplTest {
  private lateinit var getTotalPengeluaran: GetTotalPengeluaran
  private val pengeluaranRepository: PengeluaranRepository = mock()

  @Test
  fun `GetTotalPengeluaran, should success`() = runBlocking {
    getTotalPengeluaran = GetTotalPengeluaranImpl(pengeluaranRepository)

    val actualTotalPengeluaran = PengeluaranDummy.getTotalPengeluaran()

    whenever(pengeluaranRepository.getTotalPengeluaran())
      .thenReturn(flow { emit(actualTotalPengeluaran) })

    getTotalPengeluaran.invoke().test {
      val expectedVal = awaitItem()
      assertEquals(actualTotalPengeluaran, expectedVal)
      awaitComplete()
    }
  }
}