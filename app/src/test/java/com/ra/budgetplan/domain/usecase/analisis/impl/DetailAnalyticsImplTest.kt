package com.ra.budgetplan.domain.usecase.analisis.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.entity.DetailPendapatan
import com.ra.budgetplan.domain.entity.DetailPengeluaran
import com.ra.budgetplan.domain.repository.PendapatanRepository
import com.ra.budgetplan.domain.repository.PengeluaranRepository
import com.ra.budgetplan.domain.usecase.analisis.DetailAnalytics
import com.ra.budgetplan.dummy.model.AkunDummy
import com.ra.budgetplan.dummy.model.KategoriDummy
import com.ra.budgetplan.dummy.model.PendapatanDummy
import com.ra.budgetplan.dummy.model.PengeluaranDummy
import com.ra.budgetplan.presentation.ui.transaction.TransactionType
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class DetailAnalyticsImplTest {
  private lateinit var detailAnalytics: DetailAnalytics
  private val pengeluaranRepository: PengeluaranRepository = mock()
  private val pendapatanRepository: PendapatanRepository = mock()

  @Test
  fun `DetailAnalytics for Expense, should success`() = runBlocking {
    detailAnalytics = DetailAnalyticsImpl(pengeluaranRepository, pendapatanRepository)

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]
    val actualKategori = KategoriDummy.getAllKategori()[0]

    val actualDetailPengeluaran1 = DetailPengeluaran(
      akun = actualAkun,
      pengeluaran = actualPengeluaran,
      kategori = actualKategori
    )

    val actualDetailPengeluaran2 = DetailPengeluaran(
      akun = actualAkun,
      pengeluaran = actualPengeluaran,
      kategori = actualKategori
    )

    val actualListDetail = listOf(actualDetailPengeluaran1, actualDetailPengeluaran2)

    whenever(pengeluaranRepository.getPengeluaranByDate(fromDate, toDate))
      .thenReturn(actualListDetail)

    val expectedVal = detailAnalytics.invoke(TransactionType.EXPENSE, fromDate, toDate)

    assertEquals(actualListDetail.size, expectedVal.data?.size)
  }

  @Test
  fun `DetailAnalytics for Income, should success`() = runBlocking {
    detailAnalytics = DetailAnalyticsImpl(pengeluaranRepository, pendapatanRepository)

    val actualPendapatan = PendapatanDummy.getAllPendapatan()[0]
    val actualKategori = KategoriDummy.getAllKategori()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]

    val actualDetailPendapatan1 = DetailPendapatan(
      kategori = actualKategori,
      akun = actualAkun,
      pendapatan = actualPendapatan
    )

    val actualDetailPendapatan2 = DetailPendapatan(
      kategori = actualKategori,
      akun = actualAkun,
      pendapatan = actualPendapatan
    )

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    val actualList = listOf(actualDetailPendapatan1, actualDetailPendapatan2)

    whenever(pendapatanRepository.getPendapatanByDate(fromDate, toDate))
      .thenReturn(actualList)

    val expectedVal = detailAnalytics.invoke(TransactionType.INCOME, fromDate, toDate)

    assertEquals(actualList.size, expectedVal.data?.size)
  }
}