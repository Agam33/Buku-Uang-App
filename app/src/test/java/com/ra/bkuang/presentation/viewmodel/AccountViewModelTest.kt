package com.ra.bkuang.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.usecase.akun.AkunOverallMoney
import com.ra.bkuang.domain.usecase.akun.DeleteAkun
import com.ra.bkuang.domain.usecase.akun.FindAllAkun
import com.ra.bkuang.domain.usecase.akun.SaveAkun
import com.ra.bkuang.domain.usecase.akun.UpdateAkun
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.GetTotalPendapatan
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.GetTotalPengeluaran
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.PendapatanDummy
import com.ra.bkuang.dummy.model.PengeluaranDummy
import com.ra.bkuang.presentation.util.Extension.toFormatRupiah
import com.ra.bkuang.util.MainDispatcherRule
import com.ra.bkuang.domain.util.ResourceState
import com.ra.bkuang.util.getOrAwaitValue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.rules.TestRule

class AccountViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Rule
  @JvmField
  var rule: TestRule = InstantTaskExecutorRule()

  private lateinit var accountViewModel: AccountViewModel
  private val saveAkun: SaveAkun = mock()
  private val deleteAkun: DeleteAkun = mock()
  private val updateAkun: UpdateAkun = mock()
  private val findAllAkun: FindAllAkun = mock()
  private val akunOverallMoney: AkunOverallMoney = mock()
  private val getTotalPengeluaran: GetTotalPengeluaran = mock()
  private val getTotalPendapatan: GetTotalPendapatan = mock()

  @Before
  fun init() {
    accountViewModel = AccountViewModel(
      saveAkun = saveAkun,
      deleteAkun = deleteAkun,
      updateAkun = updateAkun,
      findAllAkun = findAllAkun,
      akunOverallMoney = akunOverallMoney,
      getTotalPengeluaran = getTotalPengeluaran,
      getTotalPendapatan = getTotalPendapatan
    )
  }

  @Test
  fun `SetRvState, should be success`() {
    val actualState = true

    accountViewModel.setRvState(actualState)

    val expectedVal = accountViewModel.rvAccountState.getOrAwaitValue()

    assertEquals(actualState, expectedVal)
  }

  @Test
  fun `SetEmptyLayout, should be success`() {
    val actualState = true

    accountViewModel.setEmptyLayoutState(actualState)

    val expectedVal = accountViewModel.emptyMessageState.getOrAwaitValue()

    assertEquals(actualState, expectedVal)
  }

  @Test
  fun `GetOverallMoney, should be success`() = runTest {
    val totalAccountMoney = AkunDummy.getTotalMoney()
    val totalIncomeMoney = PendapatanDummy.getTotalPendapatan()
    val totalExpenseMoney = PengeluaranDummy.getTotalPengeluaran()

    whenever(akunOverallMoney.invoke())
      .thenReturn(flow { emit(totalAccountMoney) })
    whenever(getTotalPendapatan.invoke())
      .thenReturn(flow { emit(totalIncomeMoney) })
    whenever(getTotalPengeluaran.invoke())
      .thenReturn(flow { emit(totalExpenseMoney) })

    accountViewModel.getOverallMoney()

    accountViewModel.totalAccountMoney.observeForever {
      assertEquals(totalAccountMoney.toFormatRupiah(), it)
    }
    accountViewModel.totalIncome.observeForever {
      assertEquals(totalIncomeMoney.toFormatRupiah(), it)
    }
    accountViewModel.totalExpense.observeForever {
      assertEquals(totalExpenseMoney.toFormatRupiah(), it)
    }

    verify(akunOverallMoney).invoke()
    verify(getTotalPendapatan).invoke()
    verify(getTotalPengeluaran).invoke()
  }

  @Test
  fun `CreateAccount, should be success`() = runTest {
    val actualAccount = AkunDummy.getAllAccounts()[0].toModel()

    whenever(saveAkun.invoke(actualAccount))
      .thenReturn(Unit)

    accountViewModel.createAccount(actualAccount)

    verify(saveAkun).invoke(actualAccount)
  }

  @Test
  fun `Get All Account, should be success`() = runTest {
    val actualListAkun = AkunDummy.getAllAccounts().map { it.toModel() }

    whenever(findAllAkun.invoke())
      .thenReturn(actualListAkun)

    accountViewModel.getAllAccount()

    val expectedVal = accountViewModel.accounts.getOrAwaitValue()

    verify(findAllAkun).invoke()

    assertEquals(actualListAkun, expectedVal.data)
    assertEquals(actualListAkun.size, expectedVal.data?.size)
  }

  @Test
  fun `UpdateAccount, should be success`() = runTest {
    val actualAccount = AkunDummy.getAllAccounts()[0].toModel()

    whenever(updateAkun.invoke(actualAccount))
      .thenReturn(Unit)

    accountViewModel.updateAccount(actualAccount)

    verify(updateAkun).invoke(actualAccount)
  }

  @Test
  fun `DeleteAccount, should be success`() = runTest {
    val actualAccount = AkunDummy.getAllAccounts()[0].toModel()
    val actualResourceState = ResourceState.SUCCESS

    whenever(deleteAkun.invoke(actualAccount))
      .thenReturn(flow { emit(actualResourceState) })

    accountViewModel.deleteAccount(actualAccount).test {
      val expectedItem = awaitItem()
      assertEquals(actualResourceState, expectedItem)
      awaitComplete()
    }

    verify(deleteAkun).invoke(actualAccount)
  }
}