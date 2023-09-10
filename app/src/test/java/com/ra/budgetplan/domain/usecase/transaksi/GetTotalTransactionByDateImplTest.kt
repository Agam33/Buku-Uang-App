package com.ra.budgetplan.domain.usecase.transaksi

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.repository.PendapatanRepository
import com.ra.budgetplan.domain.repository.PengeluaranRepository
import com.ra.budgetplan.dummy.model.PendapatanDummy
import com.ra.budgetplan.dummy.model.PengeluaranDummy
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class GetTotalTransactionByDateImplTest {
  private lateinit var getTotalTransactionByDate: GetTotalTransactionByDate
  private val pendapatanRepository: PendapatanRepository = mock()
  private val pengeluaranRepository: PengeluaranRepository = mock()

  @Test
  fun `GetTotalTransactionByDate, should success`() = runBlocking {
    getTotalTransactionByDate = GetTotalTransactionByDateImpl(pendapatanRepository, pengeluaranRepository)

    val actualPendapatan = PendapatanDummy.getTotalPendapatan()
    val actualPengeluaran = PengeluaranDummy.getTotalPengeluaran()

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    whenever(pengeluaranRepository.getTotalPengeluaranByDate(fromDate, toDate))
      .thenReturn(flow { emit(actualPengeluaran) } )

    whenever(pendapatanRepository.getTotalPendapatanByDate(fromDate, toDate))
      .thenReturn(flow { emit(actualPendapatan) })

    getTotalTransactionByDate.invoke(fromDate, toDate).test {
      val expectedVal = awaitItem()
      assertEquals(actualPendapatan - actualPengeluaran, expectedVal)
      awaitComplete()
    }
  }
}