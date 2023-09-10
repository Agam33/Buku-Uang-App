package com.ra.budgetplan.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.entity.DetailPengeluaran
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.AnalyticModel
import com.ra.budgetplan.domain.usecase.analisis.DetailAnalytics
import com.ra.budgetplan.domain.usecase.analisis.ShowAnalyticList
import com.ra.budgetplan.dummy.model.AkunDummy
import com.ra.budgetplan.dummy.model.KategoriDummy
import com.ra.budgetplan.dummy.model.PengeluaranDummy
import com.ra.budgetplan.presentation.ui.transaction.TransactionDetail
import com.ra.budgetplan.presentation.ui.transaction.TransactionType
import com.ra.budgetplan.util.MainDispatcherRule
import com.ra.budgetplan.util.Resource
import com.ra.budgetplan.util.getOrAwaitValue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.rules.TestRule
import java.time.LocalDateTime

class AnalyticViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Rule
  @JvmField
  var rule: TestRule = InstantTaskExecutorRule()

  private lateinit var analyticViewModel: AnalyticViewModel
  private val showAnalyticList: ShowAnalyticList = mock()
  private val detailAnalytics: DetailAnalytics = mock()

  @Before
  fun init() {
    analyticViewModel = AnalyticViewModel(showAnalyticList, detailAnalytics)
  }

  @Test
  fun `GetAnalyticList, should be success`() = runTest {
    val analytics = listOf(
      AnalyticModel("A", 10000, 30.0),
      AnalyticModel("B", 20000, 10.0),
      AnalyticModel("C", 30000, 40.0)
    )

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    whenever(showAnalyticList.invoke(TransactionType.EXPENSE, fromDate, toDate))
      .thenReturn(Resource.Success(analytics))

    analyticViewModel.getAnalyticList(TransactionType.EXPENSE, fromDate, toDate)

    val expectedVal = analyticViewModel.analyticList.getOrAwaitValue().data

    Assertions.assertEquals(expectedVal?.size, analytics.size)
  }

  @Test
  fun `GetDetailAnalytics, should be success`() = runTest {
    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]
    val actualKategori = KategoriDummy.getAllKategori()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]

    val detail1: TransactionDetail = DetailPengeluaran(
      akun = actualAkun,
      kategori = actualKategori,
      pengeluaran = actualPengeluaran
    ).toModel()

    val detail2: TransactionDetail = DetailPengeluaran(
      akun = actualAkun,
      kategori = actualKategori,
      pengeluaran = actualPengeluaran
    ).toModel()

    val listTransaction = listOf(
      detail1,
      detail2
    )

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    whenever(detailAnalytics.invoke(TransactionType.EXPENSE, fromDate, toDate))
      .thenReturn(Resource.Success(listTransaction))

    analyticViewModel.getDetailAnalytic(TransactionType.EXPENSE, fromDate, toDate)

    val expectedVal = analyticViewModel.detailTransactions.getOrAwaitValue()

    Assertions.assertEquals(expectedVal.data?.size, listTransaction.size)
  }
}