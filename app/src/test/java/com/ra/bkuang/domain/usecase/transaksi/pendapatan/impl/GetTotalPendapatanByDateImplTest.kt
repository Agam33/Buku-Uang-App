package com.ra.bkuang.domain.usecase.transaksi.pendapatan.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.repository.PendapatanRepository
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.GetTotalPendapatanByDate
import com.ra.bkuang.dummy.model.PendapatanDummy
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class GetTotalPendapatanByDateImplTest {
  private val repository: PendapatanRepository = mock()
  private lateinit var getPendapatanByDate: GetTotalPendapatanByDate

  @Test
  fun `GetPendapatanByDate, should success`() = runBlocking {
    getPendapatanByDate = GetTotalPendapatanByDateImpl(repository)

    val actualPendapatan = PendapatanDummy.getTotalPendapatan()
    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    whenever(repository.getTotalPendapatanByDate(fromDate, toDate))
      .thenReturn(flow { emit(actualPendapatan) })

    val expectedVal = getPendapatanByDate.invoke(fromDate, toDate).first()

    assertEquals(actualPendapatan, expectedVal)
  }
}