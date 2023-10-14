package com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.entity.DetailPengeluaran
import com.ra.bkuang.domain.repository.PengeluaranRepository
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.FindDetailPengeluaranById
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.KategoriDummy
import com.ra.bkuang.dummy.model.PengeluaranDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class FindDetailPengeluaranByIdImplTest {
  private lateinit var findDetailPengeluaranById: FindDetailPengeluaranById
  private val pengeluaranRepository: PengeluaranRepository = mock()

  @Test
  fun `FindDetailPengeluaranById, should success`() = runBlocking {
    findDetailPengeluaranById = FindDetailPengeluaranByIdImpl(pengeluaranRepository)

    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]
    val actualKategori = KategoriDummy.getAllKategori()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]

    val actualDetail = DetailPengeluaran(
      akun = actualAkun,
      kategori = actualKategori,
      pengeluaran = actualPengeluaran
    )

    whenever(pengeluaranRepository.findDetailById(actualPengeluaran.uuid))
      .thenReturn(actualDetail)

    val expectedVal = findDetailPengeluaranById.invoke(actualPengeluaran.uuid)
    assertEquals(expectedVal, actualDetail)
    assertEquals(expectedVal.pengeluaran.uuid, actualDetail.pengeluaran.uuid)
  }
}