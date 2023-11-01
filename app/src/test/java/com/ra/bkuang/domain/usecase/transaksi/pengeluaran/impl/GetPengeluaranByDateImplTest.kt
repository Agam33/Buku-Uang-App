package com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.data.entity.DetailPengeluaran
import com.ra.bkuang.domain.repository.PengeluaranRepository
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.GetPengeluaranByDate
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.KategoriDummy
import com.ra.bkuang.dummy.model.PengeluaranDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class GetPengeluaranByDateImplTest {
  private lateinit var getPengeluaranByDate: GetPengeluaranByDate
  private val pengeluaranRepository: PengeluaranRepository = mock()

  @Test
  fun `GetMonthlyPengeluaran, should success`() = runBlocking {
    getPengeluaranByDate = GetPengeluaranByDateImpl(pengeluaranRepository)

    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]
    val actualKategori = KategoriDummy.getAllKategori()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]

    val actualDetail1 = DetailPengeluaran(
      akun = actualAkun,
      kategori = actualKategori,
      pengeluaran = actualPengeluaran
    )

    val actualDetail2 = DetailPengeluaran(
      akun = actualAkun,
      kategori = actualKategori,
      pengeluaran = actualPengeluaran
    )

    val actualListDetail = listOf(actualDetail1, actualDetail2)

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    whenever(pengeluaranRepository.getPengeluaranByDate(fromDate, toDate))
      .thenReturn(actualListDetail)

    val expectedVal = getPengeluaranByDate.invoke(fromDate, toDate)
    assertEquals(actualListDetail, expectedVal)
    assertEquals(actualListDetail.size, expectedVal.size)
  }
}