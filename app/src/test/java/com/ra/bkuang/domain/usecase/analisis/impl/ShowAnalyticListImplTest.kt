package com.ra.bkuang.domain.usecase.analisis.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.data.local.entity.DetailPendapatan
import com.ra.bkuang.data.local.entity.DetailPengeluaran
import com.ra.bkuang.domain.model.AnalyticModel
import com.ra.bkuang.domain.repository.PendapatanRepository
import com.ra.bkuang.domain.repository.PengeluaranRepository
import com.ra.bkuang.domain.usecase.analisis.ShowAnalyticList
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.KategoriDummy
import com.ra.bkuang.dummy.model.PendapatanDummy
import com.ra.bkuang.dummy.model.PengeluaranDummy
import com.ra.bkuang.presentation.ui.features.transaction.TransactionType
import com.ra.bkuang.util.calculatePercent
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.UUID

internal class ShowAnalyticListImplTest {
  private lateinit var showAnalyticList: ShowAnalyticList
  private val pengeluaranRepository: PengeluaranRepository = mock()
  private val pendapatanRepository: PendapatanRepository = mock()

  @Test
  fun `ShowAnalyticList for Income, should be success`() = runBlocking {
    showAnalyticList = ShowAnalyticListImpl(pendapatanRepository, pengeluaranRepository)

    val actualAkun = AkunDummy.getAllAccounts()[0]
    val actualKategori = KategoriDummy.getAllKategori()[0]
    val actualPendapatan = PendapatanDummy.getAllPendapatan()[0]

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

    val actualListPendapatanDetail = listOf(actualDetailPendapatan1, actualDetailPendapatan2)
    val actualTotalPendapatan = PendapatanDummy.getTotalPendapatan()
    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    whenever(pendapatanRepository.getPendapatanByDate(fromDate, toDate))
      .thenReturn(actualListPendapatanDetail)

    whenever(pendapatanRepository.getTotalPendapatan(fromDate, toDate))
      .thenReturn(actualTotalPendapatan)

    val mapPendapatan = HashMap<UUID, AnalyticModel>()
    for(item in actualListPendapatanDetail) {
      val id = item.kategori.uuid
      val prevModel = mapPendapatan.getOrDefault(id, AnalyticModel(name = item.kategori.nama))
      prevModel.total += item.pendapatan.jumlah
      mapPendapatan[id] = prevModel
    }

    val analyticModels = ArrayList<AnalyticModel>()
    for(key in mapPendapatan.keys) {
      val model = mapPendapatan[key] ?: AnalyticModel()
      model.percent = calculatePercent(model.total, actualTotalPendapatan)
      analyticModels.add(model)
    }

    val expectedVal = showAnalyticList.invoke(TransactionType.INCOME, fromDate, toDate)

    Assertions.assertEquals(analyticModels.size, expectedVal.data?.size)
  }

  @Test
  fun `ShowAnalyticList for Expense, should be success`() = runBlocking {
    showAnalyticList = ShowAnalyticListImpl(pendapatanRepository, pengeluaranRepository)

    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]
    val actualTotalPengeluaran = PengeluaranDummy.getTotalPengeluaran()
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

    val actualListPengeluaranDetail = listOf(actualDetailPengeluaran1, actualDetailPengeluaran2)
    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    whenever(pengeluaranRepository.getPengeluaranByDate(fromDate, toDate))
      .thenReturn(actualListPengeluaranDetail)

    whenever(pengeluaranRepository.getTotalPengeluaran(fromDate, toDate))
      .thenReturn(actualTotalPengeluaran)

    val mapPengeluaran = HashMap<UUID, AnalyticModel>()
    for(item in actualListPengeluaranDetail) {
      val id = item.kategori.uuid
      val prevModel = mapPengeluaran.getOrDefault(id, AnalyticModel(name = item.kategori.nama))
      prevModel.total += item.pengeluaran.jumlah
      mapPengeluaran[id] = prevModel
    }

    val analyticModels = ArrayList<AnalyticModel>()
    for(key in mapPengeluaran.keys) {
      val model = mapPengeluaran[key] ?: AnalyticModel()
      model.percent = calculatePercent(model.total, actualTotalPengeluaran)
      analyticModels.add(model)
    }

    val expectedVal = showAnalyticList.invoke(TransactionType.EXPENSE, fromDate, toDate)

    Assertions.assertEquals(analyticModels.size, expectedVal.data?.size)
  }

  @Test
  fun `ShowAnalyticList for Transfer, should be success but with null value`() = runBlocking {
    showAnalyticList = ShowAnalyticListImpl(pendapatanRepository, pengeluaranRepository)

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    val expectedVal = showAnalyticList.invoke(TransactionType.TRANSFER, fromDate, toDate)

    Assertions.assertEquals(null, expectedVal.data)
  }

  @Test
  fun `ShowAnalyticList for Income, should be empty`() = runBlocking {
    showAnalyticList = ShowAnalyticListImpl(pendapatanRepository, pengeluaranRepository)

    val actualTotalPendapatan = PendapatanDummy.getTotalPendapatan()
    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    whenever(pendapatanRepository.getPendapatanByDate(fromDate, toDate))
      .thenReturn(listOf())

    whenever(pendapatanRepository.getTotalPendapatan(fromDate, toDate))
      .thenReturn(actualTotalPendapatan)

    val expectedVal = showAnalyticList.invoke(TransactionType.INCOME, fromDate, toDate)

    Assertions.assertEquals(null, expectedVal.data)
  }

  @Test
  fun `ShowAnalyticList for Expense, should be empty`() = runBlocking {
    showAnalyticList = ShowAnalyticListImpl(pendapatanRepository, pengeluaranRepository)

    val actualTotalPengeluaran = PengeluaranDummy.getTotalPengeluaran()

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    whenever(pengeluaranRepository.getPengeluaranByDate(fromDate, toDate))
      .thenReturn(listOf())

    whenever(pengeluaranRepository.getTotalPengeluaran(fromDate, toDate))
      .thenReturn(actualTotalPengeluaran)

    val expectedVal = showAnalyticList.invoke(TransactionType.EXPENSE, fromDate, toDate)

    Assertions.assertEquals(null, expectedVal.data)
  }
}