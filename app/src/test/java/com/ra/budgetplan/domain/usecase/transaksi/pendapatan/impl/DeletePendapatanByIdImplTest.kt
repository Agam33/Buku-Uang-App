package com.ra.budgetplan.domain.usecase.transaksi.pendapatan.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.PendapatanRepository
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.DeletePendapatanById
import com.ra.budgetplan.dummy.model.AkunDummy
import com.ra.budgetplan.dummy.model.PendapatanDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DeletePendapatanByIdImplTest {
  private lateinit var deletePendapatanById: DeletePendapatanById
  private val pendapatanRepository: PendapatanRepository = mock()
  private val akunRepository: AkunRepository = mock()

  @Test
  fun `DeletePendapatanById, should success`() = runBlocking {
    deletePendapatanById = DeletePendapatanByIdImpl(pendapatanRepository, akunRepository)

    val actualPendapatan = PendapatanDummy.getAllPendapatan()[0]
    val actualId = actualPendapatan.uuid

    val actualAkun = AkunDummy.getAllAccounts()[0]

    whenever(pendapatanRepository.findById(actualId)).thenReturn(actualPendapatan)
    whenever(pendapatanRepository.delete(actualPendapatan)).thenReturn(Unit)
    whenever(akunRepository.findById(actualPendapatan.idTabungan)).thenReturn(actualAkun)
    whenever(akunRepository.update(actualAkun)).thenReturn(Unit)

    val expectedVal = deletePendapatanById.invoke(actualId)

    assertEquals(expectedVal, Unit)
  }
}