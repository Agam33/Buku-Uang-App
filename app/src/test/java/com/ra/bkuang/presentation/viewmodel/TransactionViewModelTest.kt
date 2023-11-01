package com.ra.bkuang.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.data.local.preferences.UserSettingPref
import com.ra.bkuang.data.entity.DetailPendapatan
import com.ra.bkuang.data.entity.DetailPengeluaran
import com.ra.bkuang.data.entity.DetailTransfer
import com.ra.bkuang.data.entity.TipeKategori
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.usecase.akun.FindAkunById
import com.ra.bkuang.domain.usecase.akun.FindAllAkun
import com.ra.bkuang.domain.usecase.akun.FindCategoryByType
import com.ra.bkuang.domain.usecase.transaksi.GetTotalTransactionByDate
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.DeletePendapatanById
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.GetPendapatanByDate
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.GetPendapatanById
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.GetTotalPendapatanByDate
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.SavePendapatan
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.UpdatePendapatan
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.DeletePengeluaranById
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.GetPengeluaranByDate
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.GetPengeluaranById
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.GetTotalPengeluaranByDate
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.SavePengeluaran
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.UpdatePengeluaran
import com.ra.bkuang.domain.usecase.transaksi.transfer.DeleteTransferById
import com.ra.bkuang.domain.usecase.transaksi.transfer.GetTransferByDate
import com.ra.bkuang.domain.usecase.transaksi.transfer.GetTransferById
import com.ra.bkuang.domain.usecase.transaksi.transfer.SaveTransfer
import com.ra.bkuang.domain.usecase.transaksi.transfer.UpdateTransfer
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.KategoriDummy
import com.ra.bkuang.dummy.model.PendapatanDummy
import com.ra.bkuang.dummy.model.PengeluaranDummy
import com.ra.bkuang.dummy.model.TransferDummy
import com.ra.bkuang.presentation.ui.transaction.TransactionDetail
import com.ra.bkuang.presentation.ui.transaction.adapter.DateViewType
import com.ra.bkuang.util.MainDispatcherRule
import com.ra.bkuang.util.Resource
import com.ra.bkuang.util.ResourceState
import com.ra.bkuang.util.getOrAwaitValue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.rules.TestRule
import java.time.LocalDateTime

class TransactionViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Rule
  @JvmField
  var rule: TestRule = InstantTaskExecutorRule()

  private lateinit var transactionViewModel: TransactionViewModel
  private val saveTransfer: SaveTransfer = mock()
  private val savePengeluaran: SavePengeluaran = mock()
  private val savePendapatan: SavePendapatan = mock()
  private val findAllAkun: FindAllAkun = mock()
  private val findKategoriCategoryByType: FindCategoryByType = mock()
  private val getPengeLuaranByDate: GetPengeluaranByDate = mock()
  private val getPendapatanByDate: GetPendapatanByDate = mock()
  private val getTransferByDate: GetTransferByDate = mock()
  private val userSettingPref: UserSettingPref = mock()
  private val getTotalPendapatanByDate: GetTotalPendapatanByDate = mock()
  private val getTotalPengeluaranByDate: GetTotalPengeluaranByDate = mock()
  private val getTotalTransactionByDate: GetTotalTransactionByDate = mock()
  private val deletePengeluaranById: DeletePengeluaranById = mock()
  private val deleteTransferById: DeleteTransferById = mock()
  private val deletePendapatanById: DeletePendapatanById = mock()
  private val getPendapatanById: GetPendapatanById = mock()
  private val getPengeluaranById: GetPengeluaranById = mock()
  private val getTransferById: GetTransferById = mock()
  private val updateTransfer: UpdateTransfer = mock()
  private val updatePendapatan: UpdatePendapatan = mock()
  private val updatePengeluaran: UpdatePengeluaran = mock()
  private val findAkunById: FindAkunById = mock()

  @Before
  fun init() {
    transactionViewModel = TransactionViewModel(
      saveTransfer, savePengeluaran, savePendapatan,
      findAllAkun, findKategoriCategoryByType, getPengeLuaranByDate,
      getPendapatanByDate, getTransferByDate, userSettingPref,
      getTotalPendapatanByDate, getTotalPengeluaranByDate, getTotalTransactionByDate,
      deletePengeluaranById, deleteTransferById, deletePendapatanById,
      getPendapatanById, getPengeluaranById, getTransferById,
      updateTransfer, updatePendapatan, updatePengeluaran, findAkunById
    )
  }

  @Test
  fun `checkAccountMoney, should be success`() = runTest {
    val akun = AkunDummy.getAllAccounts()[0].toModel()
    whenever(findAkunById.invoke(akun.uuid)).thenReturn(akun)

    val amount = 1000

    transactionViewModel.checkAccountMoney(akun.uuid, amount) {
      transactionViewModel.setSaveTransactionState(true)
    }

    val status = transactionViewModel.saveTransactionState.getOrAwaitValue()

    assertEquals(status, true)
  }

  @Test
  fun `UpdateTransfer, should be success`() = runTest {
    val transfer = TransferDummy.getAllTransfer()[0].toModel()

    val actualValue = ResourceState.SUCCESS

    whenever(updateTransfer.invoke(transfer, transfer))
      .thenReturn(actualValue)

    val expectedVal = transactionViewModel.updateTransfer(transfer, transfer)

    verify(updateTransfer).invoke(transfer, transfer)

    assertEquals(actualValue, expectedVal)
  }

  @Test
  fun `UpdatePengeluaran, should be success`() = runTest {
    val pengeluaran = PengeluaranDummy.getAllPengeluaran()[0].toModel()

    val actualVal = ResourceState.SUCCESS

    whenever(updatePengeluaran.invoke(pengeluaran, pengeluaran))
      .thenReturn(actualVal)

    val expectedVal = transactionViewModel.updatePengeluaran(pengeluaran, pengeluaran)

    verify(updatePengeluaran).invoke(pengeluaran, pengeluaran)

    assertEquals(actualVal, expectedVal)
  }

  @Test
  fun `UpdatePendapatan, should be success`() = runTest {
    val pendapatan = PendapatanDummy.getAllPendapatan()[0].toModel()

    val actualVal = ResourceState.SUCCESS

    whenever(updatePendapatan.invoke(pendapatan, pendapatan))
      .thenReturn(actualVal)

    val expectedVal = transactionViewModel.updatePendapatan(pendapatan, pendapatan)

    verify(updatePendapatan).invoke(pendapatan, pendapatan)

    assertEquals(actualVal, expectedVal)
  }

  @Test
  fun `DeleteTransferById, should be success`() = runTest {
    val transferId = TransferDummy.getAllTransfer()[0].toModel().uuid

    val actualVal = ResourceState.SUCCESS
    whenever(deleteTransferById.invoke(transferId))
      .thenReturn(actualVal)

    val expectedVal = transactionViewModel.deleteTransferById(transferId)

    verify(deleteTransferById).invoke(transferId)

    assertEquals(expectedVal, actualVal)
  }

  @Test
  fun `DeletePengeluaranById, should be success`() = runTest {
    val pengeluaranId = PengeluaranDummy.getAllPengeluaran()[0].toModel().uuid

    val actualVal = ResourceState.SUCCESS
    whenever(deletePengeluaranById.invoke(pengeluaranId))
      .thenReturn(actualVal)

    val expectedVal = transactionViewModel.deletePengeluaranById(pengeluaranId)

    verify(deletePengeluaranById).invoke(pengeluaranId)

    assertEquals(expectedVal, actualVal)
  }

  @Test
  fun `DeletePendapatanById, should be success`() = runTest {
    val pendapatanId = PendapatanDummy.getAllPendapatan()[0].toModel().uuid

    val actualVal = ResourceState.SUCCESS
    whenever(deletePendapatanById.invoke(pendapatanId))
      .thenReturn(actualVal)

    val expectedVal = transactionViewModel.deletePendapatanById(pendapatanId)

    verify(deletePendapatanById).invoke(pendapatanId)

    assertEquals(actualVal, expectedVal)
  }

  @Test
  fun `SaveTransfer, should be success`() = runTest {
    val transferModel = TransferDummy.getAllTransfer()[0].toModel()

    val actualVal = ResourceState.SUCCESS
    whenever(saveTransfer.invoke(transferModel))
      .thenReturn(actualVal)

    val expectedVal = transactionViewModel.saveTransfer(transferModel)

    verify(saveTransfer).invoke(transferModel)

    assertEquals(actualVal, expectedVal)
  }

  @Test
  fun `SavePendapatan, should be success`() = runTest {
    val pendapatanModel = PendapatanDummy.getAllPendapatan()[0].toModel()

    val actualVal = ResourceState.SUCCESS
    whenever(savePendapatan.invoke(pendapatanModel))
      .thenReturn(actualVal)

    val expectedVal = transactionViewModel.savePendapatan(pendapatanModel)

    verify(savePendapatan).invoke(pendapatanModel)

    assertEquals(actualVal, expectedVal)
  }

  @Test
  fun `SavePengeluaran, should be success`() = runTest {
    val pengeluaranModel = PengeluaranDummy.getAllPengeluaran()[0].toModel()

    val actualVal = ResourceState.SUCCESS
    whenever(savePengeluaran.invoke(pengeluaranModel))
      .thenReturn(actualVal)

    val expectedVal = transactionViewModel.savePengeluaran(pengeluaranModel)

    verify(savePengeluaran).invoke(pengeluaranModel)

    assertEquals(actualVal, expectedVal)
  }

  @Test
  fun `SetCategoryByType, should be success`() {
    val categories = KategoriDummy.getAllKategori().map { it.toModel() }

    whenever(findKategoriCategoryByType.invoke(TipeKategori.PENGELUARAN))
      .thenReturn(flow { emit(Resource.Success(categories)) })

    transactionViewModel.setCategoryByType(TipeKategori.PENGELUARAN)

    val expectedVal = transactionViewModel.listCategoryByType.getOrAwaitValue()
    assertEquals(expectedVal, categories)
  }

  @Test
  fun `GetAllAccount, should be success`() = runTest {
    val accounts = AkunDummy.getAllAccounts().map { it.toModel() }

    whenever(findAllAkun.invoke())
      .thenReturn(accounts)

    transactionViewModel.getAllAccount()

    val expectedVal = transactionViewModel.listAccount.getOrAwaitValue()
    assertEquals(expectedVal, accounts)
    assertEquals(expectedVal.size, accounts.size)
  }

  @Test
  fun `GetTransferByDate, should be success`() = runTest {
    val actualTransfer = TransferDummy.getAllTransfer()[0]
    val actualFromAkun = AkunDummy.getAllAccounts()[0]
    val actualToAkun = AkunDummy.getAllAccounts()[1]

    val actualDetail1 = DetailTransfer(
      transfer = actualTransfer,
      fromAkun = actualFromAkun,
      toAkun =  actualToAkun
    )

    val actualDetail2 = DetailTransfer(
      transfer = actualTransfer,
      fromAkun = actualFromAkun,
      toAkun =  actualToAkun
    )

    val actualListDetail = listOf(actualDetail1, actualDetail2)

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    whenever(getTransferByDate.invoke(fromDate, toDate))
      .thenReturn(actualListDetail)

    transactionViewModel.getTransferByDate(fromDate, toDate)

    val expectedVal = transactionViewModel.listTransfer.getOrAwaitValue()
    assertEquals(expectedVal.data, actualListDetail)
    assertEquals(expectedVal.data?.size, actualListDetail.size)
  }

  @Test
  fun `GetPendapatanByDate, should be success`() = runTest {
    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

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

    whenever(getPendapatanByDate.invoke(fromDate, toDate))
      .thenReturn(actualListDetail)

    transactionViewModel.getPendapatanByDate(fromDate, toDate)

    val expectedVal = transactionViewModel.incomes.getOrAwaitValue()
    assertEquals(expectedVal.data?.size, actualListDetail.size)
    assertEquals(expectedVal.data, actualListDetail)
  }

  @Test
  fun `GetPengeluaranByDate, should be success`() = runTest {
    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

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

    whenever(getPengeLuaranByDate.invoke(fromDate, toDate))
      .thenReturn(actualListDetail)

    transactionViewModel.getPengeluaranByDate(fromDate, toDate)

    val expectedVal = transactionViewModel.listPengeluaran.getOrAwaitValue()
    assertEquals(expectedVal.data?.size, actualListDetail.size)
    assertEquals(expectedVal.data, actualListDetail)
  }

  @Test
  fun `GetTotalByDate, should be success`() {
   // Todo
  }

  @Test
  fun `GetTotalPengeluaranByDate, should be success`() {

  }

  @Test
  fun `GetTotalPendapatanByDate, should be success`() = runTest {
    // Todo


//    val fromDate = LocalDateTime.now()
//    val toDate = LocalDateTime.now()
//    val actualTotalPendapatan = PendapatanDummy.getTotalPendapatan()
//
//    whenever(getTotalPendapatanByDate.invoke(fromDate, toDate))
//      .thenReturn(flow { emit(actualTotalPendapatan) })
//
//    transactionViewModel.getTotalPendapatanByDate(fromDate, toDate)
//
//    transactionViewModel.textPendapatan.test {
//      assertEquals(awaitItem(), actualTotalPendapatan.toFormatRupiah())
//    }
  }

  @Test
  fun `SetStateTransferListUi, should be success`() {
    val rvState = false
    val emptyState = true

    transactionViewModel.setStateTransferListUi(rvState, emptyState)

    val expectedRvState = transactionViewModel.rvTransferState.getOrAwaitValue()
    val expectedEmptyState = transactionViewModel.emptyTransferLayoutState.getOrAwaitValue()

    assertEquals(expectedRvState, rvState)
    assertEquals(expectedEmptyState, emptyState)
  }

  @Test
  fun `SetStateExpenseListUi, should be success`() {
    val rvState = false
    val emptyState = true

    transactionViewModel.setStateExpenseListUi(rvState, emptyState)

    val expectedRvState = transactionViewModel.rvExpenseState.getOrAwaitValue()
    val expectedEmptyState = transactionViewModel.emptyExpenseLayoutState.getOrAwaitValue()

    assertEquals(expectedRvState, rvState)
    assertEquals(expectedEmptyState, emptyState)
  }

  @Test
  fun `SetStateIncomeListUi, should be success`() {
    val rvState = false
    val emptyState = true

    transactionViewModel.setStateIncomeListUi(rvState, emptyState)

    val expectedRvState = transactionViewModel.rvIncomeState.getOrAwaitValue()
    val expectedEmptyState = transactionViewModel.emptyIncomeLayoutState.getOrAwaitValue()

    assertEquals(expectedRvState, rvState)
    assertEquals(expectedEmptyState, emptyState)
  }

  @Test
  fun `GetDateViewType, should be success`() {
    val viewType = DateViewType.DAILY
    val flow = flow { emit(viewType.name) }

    whenever(userSettingPref.getDateViewType())
      .thenReturn(flow)

    val expectedVal = transactionViewModel.getDateViewType().getOrAwaitValue()

    assertEquals(viewType.name, expectedVal)
  }

  @Test
  fun `SetCurrentDate, should be success`() {
    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    val pairDate = fromDate to toDate

    transactionViewModel.setCurrentDate(pairDate)

    val expectedVal = transactionViewModel.currentDate.getOrAwaitValue()

    assertEquals(pairDate.first, expectedVal.first)
    assertEquals(pairDate.second, expectedVal.second)
  }

  @Test
  fun `SetDetailTransaction, should be success`() {
    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]
    val actualKategori = KategoriDummy.getAllKategori()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]

    val actualDetail: TransactionDetail = DetailPengeluaran(
      akun = actualAkun,
      kategori = actualKategori,
      pengeluaran = actualPengeluaran
    ).toModel()

    transactionViewModel.setDetailTransaction(actualDetail)

    val expectedVal = transactionViewModel.detailTransaction.getOrAwaitValue()

    assertEquals(expectedVal, actualDetail)
    assertEquals(expectedVal.uuid, actualDetail.uuid)
  }

  @Test
  fun `GetTransferById, should be success`() = runTest {
    val actualTransfer = TransferDummy.getAllTransfer()[0].toModel()
    val actualId = actualTransfer.uuid

    whenever(getTransferById.invoke(actualId))
      .thenReturn(actualTransfer)

    transactionViewModel.getTransferById(actualId)

    val expectedVal = transactionViewModel.transferModel.getOrAwaitValue()

    assertEquals(actualTransfer, expectedVal)
    assertEquals(actualTransfer.uuid, expectedVal.uuid)
  }

  @Test
  fun `GetPengeluaranById, should be success`() = runTest {
    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0].toModel()
    val actualId = actualPengeluaran.uuid

    whenever(getPengeluaranById.invoke(actualId))
      .thenReturn(actualPengeluaran)

    transactionViewModel.getPengeluaranById(actualId)

    val expectedVal = transactionViewModel.pengeluaranModel.getOrAwaitValue()

    assertEquals(expectedVal, actualPengeluaran)
    assertEquals(expectedVal.uuid, actualPengeluaran.uuid)
  }

  @Test
  fun `GetPendapatanById, should be success`() = runTest {
    val actualPendapatan = PendapatanDummy.getAllPendapatan()[0].toModel()
    val actualId = actualPendapatan.uuid

    whenever(getPendapatanById.invoke(actualId))
      .thenReturn(actualPendapatan)

    transactionViewModel.getPendapatanById(actualId)

    val expectedVal = transactionViewModel.pendapatanModel.getOrAwaitValue()

    assertEquals(expectedVal, actualPendapatan)
    assertEquals(expectedVal.uuid, actualPendapatan.uuid)
  }
}