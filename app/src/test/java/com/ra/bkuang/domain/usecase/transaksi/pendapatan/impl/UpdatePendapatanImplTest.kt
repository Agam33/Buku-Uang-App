package com.ra.bkuang.domain.usecase.transaksi.pendapatan.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.repository.PendapatanRepository
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.UpdatePendapatan
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.PendapatanDummy
import com.ra.bkuang.domain.util.ResourceState
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class UpdatePendapatanImplTest {
  private val pendapatanRepository: PendapatanRepository = mock()
  private val akunRepository: AkunRepository = mock()
  private lateinit var updatePendapatan: UpdatePendapatan

  @Test
  fun `UpdatePendapatan, should success`() = runBlocking {
    updatePendapatan = UpdatePendapatanImpl(pendapatanRepository, akunRepository)

    val actualPendapatan = PendapatanDummy.getAllPendapatan()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]

    whenever(pendapatanRepository.update(actualPendapatan)).thenReturn(Unit)
    whenever(akunRepository.findById(actualPendapatan.idTabungan)).thenReturn(actualAkun)
    whenever(akunRepository.update(actualAkun)).thenReturn(Unit)
    whenever(akunRepository.findById(actualPendapatan.idTabungan)).thenReturn(actualAkun)
    whenever(akunRepository.update(actualAkun)).thenReturn(Unit)

    val expectedVal = updatePendapatan.invoke(actualPendapatan.toModel(), actualPendapatan.toModel())
    assertEquals(expectedVal, ResourceState.SUCCESS)
  }
}