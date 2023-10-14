package com.ra.bkuang.domain.usecase.transaksi.pendapatan.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.repository.PendapatanRepository
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.GetPendapatanById
import com.ra.bkuang.dummy.model.PendapatanDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GetPendapatanByIdImplTest {
  private val repository: PendapatanRepository = mock()
  private lateinit var getPendapatanById: GetPendapatanById

  @Test
  fun `GetPendapatanById, should success`() = runBlocking {
    getPendapatanById = GetPendapatanByIdImpl(repository)

    val actualPendapatan = PendapatanDummy.getAllPendapatan()[0]
    val actualId = actualPendapatan.uuid

    whenever(repository.findById(actualId)).thenReturn(actualPendapatan)

    val expectedVal = getPendapatanById.invoke(actualId)

    assertEquals(actualPendapatan.toModel(), expectedVal)
    assertEquals(actualPendapatan.toModel().idAkun, expectedVal.idAkun)
  }
}