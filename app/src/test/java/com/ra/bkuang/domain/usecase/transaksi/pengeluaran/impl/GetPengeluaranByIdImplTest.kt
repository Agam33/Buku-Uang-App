package com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.repository.PengeluaranRepository
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.GetPengeluaranById
import com.ra.bkuang.dummy.model.PengeluaranDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GetPengeluaranByIdImplTest {
  private lateinit var getPengeluaranById: GetPengeluaranById
  private val pengeluaranRepository: PengeluaranRepository = mock()

  @Test
  fun `GetPengeluaranById, should success`() = runBlocking {
    getPengeluaranById = GetPengeluaranByIdImpl(pengeluaranRepository)

    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]
    val actualPengeluaranId = actualPengeluaran.uuid

    whenever(pengeluaranRepository.findById(actualPengeluaranId))
      .thenReturn(actualPengeluaran)

    val expectedVal = getPengeluaranById.invoke(actualPengeluaranId)
    assertEquals(actualPengeluaran.uuid, expectedVal.uuid)
  }
}