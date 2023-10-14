package com.ra.budgetplan.domain.usecase.transaksi.pendapatan.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.PendapatanRepository
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.SavePendapatan
import com.ra.budgetplan.dummy.model.AkunDummy
import com.ra.budgetplan.dummy.model.PendapatanDummy
import com.ra.budgetplan.util.ResourceState
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SavePendapatanImplTest {
  private val pendapatanRepository: PendapatanRepository = mock()
  private val akunRepository: AkunRepository = mock()
  private lateinit var savePendapatan: SavePendapatan

  @Test
  fun `SavePendapatan, should success`() = runBlocking {
    savePendapatan = SavePendapatanImpl(pendapatanRepository, akunRepository)

    val actualPendapatan = PendapatanDummy.getAllPendapatan()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]

    whenever(akunRepository.findById(actualPendapatan.idTabungan)).thenReturn(actualAkun)
    whenever(akunRepository.update(actualAkun)).thenReturn(Unit)
    whenever(pendapatanRepository.save(actualPendapatan)).thenReturn(Unit)

    val expectedVal = savePendapatan.invoke(actualPendapatan.toModel())
    assertEquals(expectedVal, ResourceState.SUCCESS)
  }
}