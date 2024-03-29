package com.ra.bkuang.domain.usecase.transaksi.pendapatan.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.data.local.database.entity.DetailPendapatan
import com.ra.bkuang.domain.repository.PendapatanRepository
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.GetPendapatanByDate
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.KategoriDummy
import com.ra.bkuang.dummy.model.PendapatanDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime


internal class GetPendapatanByDateImplTest {
  private val repository: PendapatanRepository = mock()
  private lateinit var getPendapatanByDate: GetPendapatanByDate

  @Test
  fun `GetPendapatanByDateImpl, should success`() = runBlocking {
    getPendapatanByDate = GetPendapatanByDateImpl(repository)

    val actualPendapatan = PendapatanDummy.getAllPendapatan()
    val actualKategori = KategoriDummy.getAllKategori()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]

    val detailPendapatan1 = DetailPendapatan(
      pendapatan = actualPendapatan[0],
      kategori = actualKategori,
      akun = actualAkun
    )

    val detailPendapatan2 = DetailPendapatan(
      pendapatan = actualPendapatan[1],
      kategori = actualKategori,
      akun = actualAkun
    )

    val detailPendapatan3 = DetailPendapatan(
      pendapatan = actualPendapatan[0],
      kategori = actualKategori,
      akun = actualAkun
    )

    val actualListDetail = listOf(detailPendapatan1, detailPendapatan2, detailPendapatan3)

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    whenever(repository.getPendapatanByDate(fromDate, toDate))
      .thenReturn(actualListDetail)

    val expectedVal = getPendapatanByDate.invoke(fromDate, toDate)

    assertEquals(expectedVal, actualListDetail)
    assertEquals(expectedVal.size, actualListDetail.size)
  }
}