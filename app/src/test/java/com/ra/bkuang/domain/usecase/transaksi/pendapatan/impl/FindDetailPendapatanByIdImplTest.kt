package com.ra.bkuang.domain.usecase.transaksi.pendapatan.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.data.entity.DetailPendapatan
import com.ra.bkuang.domain.repository.PendapatanRepository
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.FindDetailPendapatanById
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.KategoriDummy
import com.ra.bkuang.dummy.model.PendapatanDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class FindDetailPendapatanByIdImplTest {
  private lateinit var findDetailPendapatanById: FindDetailPendapatanById
  private val repository: PendapatanRepository = mock()

  @Test
  fun `FindDetailPendapatanById, should success`() = runBlocking {
    findDetailPendapatanById = FindDetailPendapatanByIdImpl(repository)

    val actualPendapatan = PendapatanDummy.getAllPendapatan()[0]
    val actualKategori = KategoriDummy.getAllKategori()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]
    val actualID = actualPendapatan.uuid

    val detailPendapatan = DetailPendapatan(
      pendapatan = actualPendapatan,
      kategori = actualKategori,
      akun = actualAkun
    )

    whenever(repository.findDetailById(actualID)).thenReturn(detailPendapatan)

    val expectedVal = findDetailPendapatanById.invoke(actualID)

    assertEquals(actualID, expectedVal.pendapatan.uuid)
  }
}