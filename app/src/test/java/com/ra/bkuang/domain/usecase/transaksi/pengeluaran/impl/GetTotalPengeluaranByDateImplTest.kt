package com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.repository.PengeluaranRepository
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.GetTotalPengeluaranByDate
import com.ra.bkuang.dummy.model.PengeluaranDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class GetTotalPengeluaranByDateImplTest {
  private lateinit var getTotalPengeluaranByDate: GetTotalPengeluaranByDate
  private val pengeluaranRepository: PengeluaranRepository = mock()

  @Test
  fun `GetTotalPengeluaranByDate, should success`() = runBlocking {
    getTotalPengeluaranByDate = GetTotalPengeluaranByDateImpl(pengeluaranRepository)

    val actualTotalPengeluaran = PengeluaranDummy.getTotalPengeluaran()
    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    whenever(pengeluaranRepository.getTotalPengeluaranByDate(fromDate, toDate))
      .thenReturn(flow { emit(actualTotalPengeluaran) })

    getTotalPengeluaranByDate.invoke(fromDate, toDate).test {
      val expectedVal = awaitItem()
      assertEquals(actualTotalPengeluaran, expectedVal)
      awaitComplete()
    }
  }
}