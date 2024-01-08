package com.ra.bkuang.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.domain.usecase.akun.FindAllAkun
import com.ra.bkuang.domain.usecase.hutang.DeleteRecordPembayaranHutang
import com.ra.bkuang.domain.usecase.hutang.FindAllRecordPembayaranHutang
import com.ra.bkuang.domain.usecase.hutang.FindHutangById
import com.ra.bkuang.domain.usecase.hutang.FindHutangByIdWithFlow
import com.ra.bkuang.domain.usecase.hutang.GetSizeListPembayaranHutangById
import com.ra.bkuang.domain.usecase.hutang.SavePembayaranHutang
import com.ra.bkuang.domain.usecase.hutang.UpdatePembayaranHutang
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.HutangDummy
import com.ra.bkuang.dummy.model.PembayaranHutangDummy
import com.ra.bkuang.util.MainDispatcherRule
import com.ra.bkuang.domain.util.Resource
import com.ra.bkuang.domain.util.ResourceState
import com.ra.bkuang.util.getOrAwaitValue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.rules.TestRule
import java.util.UUID

class DetailDebtViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Rule
  @JvmField
  var rule: TestRule = InstantTaskExecutorRule()

  private lateinit var detailDebtViewModel: DetailDebtViewModel
  private val findAllAkun: FindAllAkun = mock()
  private val findAllRecordPembayaranHutang: FindAllRecordPembayaranHutang = mock()
  private val savePembayaranHutang: SavePembayaranHutang = mock()
  private val findHutangByIdWithFlow: FindHutangByIdWithFlow = mock()
  private val findHutangById: FindHutangById = mock()
  private val deleteRecordPembayaranHutang: DeleteRecordPembayaranHutang = mock()
  private val updatePembayaranHutang: UpdatePembayaranHutang = mock()
  private val getSizeListPembayaranHutangById: GetSizeListPembayaranHutangById = mock()


  @Before
  fun init() {
    detailDebtViewModel = DetailDebtViewModel(
      findAllAkun,
      findAllRecordPembayaranHutang,
      savePembayaranHutang,
      findHutangByIdWithFlow,
      findHutangById,
      deleteRecordPembayaranHutang,
      updatePembayaranHutang,
      getSizeListPembayaranHutangById
    )
  }

  @Test
  fun `GetSizeListPembayaranHutangById(), should success`() = runBlocking {
    val actualSize = 23
    val uuid = UUID.randomUUID()

    whenever(getSizeListPembayaranHutangById.invoke(uuid))
      .thenReturn( flow { emit(actualSize) } )

    detailDebtViewModel.getSizeListPembayaranHutang(uuid)

    val expectedItem = detailDebtViewModel.sizeListPembayaranHutang.getOrAwaitValue()

    assertEquals(expectedItem, actualSize.toString())
  }

  @Test
  fun `GetAllDebtRecord, should be success`() = runTest {
    val actualAkun = AkunDummy.getAllAccounts()[0].toModel()
    val actualHutang = HutangDummy.getAllHutang()[0].toModel()
    val actualPembayaranHutang = PembayaranHutangDummy.getAllPembayaranHutang()[0].toModel()
    val actualHutangId = actualHutang.uuid

    val actualDetailPembayaran1 = DetailPembayaranHutangModel(
      pembayaranHutangModel = actualPembayaranHutang,
      hutangModel = actualHutang,
      akunModel = actualAkun
    )

    val actualDetailPembayaran2 = DetailPembayaranHutangModel(
      pembayaranHutangModel = actualPembayaranHutang,
      hutangModel = actualHutang,
      akunModel = actualAkun
    )

    val actualListDetail = listOf(actualDetailPembayaran1, actualDetailPembayaran2)

    whenever(findAllRecordPembayaranHutang.invoke(actualHutangId))
      .thenReturn(Resource.Success(actualListDetail))

    detailDebtViewModel.getAllDebtRecord(actualHutangId)

    val expectedVal = detailDebtViewModel.debtRecord.getOrAwaitValue()

    assertEquals(expectedVal.data, actualListDetail)
    assertEquals(expectedVal.data?.size, actualListDetail.size)
  }

  @Test
  fun `UpdatePembayaranHutang, should be success`() = runTest {
    val actualPembayaranHutang = PembayaranHutangDummy.getAllPembayaranHutang()[0].toModel()

    val actualResourceState = ResourceState.SUCCESS

    whenever(updatePembayaranHutang.invoke(actualPembayaranHutang, actualPembayaranHutang))
      .thenReturn(flow { emit(actualResourceState) })

    detailDebtViewModel.updatePembayaranHutang(actualPembayaranHutang, actualPembayaranHutang).test {
      val expectedVal = awaitItem()
      assertEquals(actualResourceState, expectedVal)
      awaitComplete()
    }
  }

  @Test
  fun `DeleteRecordPembayaranHutang, should be success`() = runTest {
    val actualAkun = AkunDummy.getAllAccounts()[0].toModel()
    val actualHutang = HutangDummy.getAllHutang()[0].toModel()
    val actualPembayaranHutang = PembayaranHutangDummy.getAllPembayaranHutang()[0].toModel()

    val actualDetailPembayaran = DetailPembayaranHutangModel(
      pembayaranHutangModel = actualPembayaranHutang,
      hutangModel = actualHutang,
      akunModel = actualAkun
    )

    val actualResourceState = ResourceState.SUCCESS

    whenever(deleteRecordPembayaranHutang.invoke(actualDetailPembayaran))
      .thenReturn(flow { emit(actualResourceState) })

    detailDebtViewModel.deleteRecordPembayaranHutang(actualDetailPembayaran).test {
      val expectedVal = awaitItem()
      assertEquals(actualResourceState, expectedVal)
      awaitComplete()
    }
  }

  @Test
  fun `SavePembayaranHutang, should be success`() = runTest {
    val actualPembayaranHutang = PembayaranHutangDummy.getAllPembayaranHutang()[0].toModel()

    val actualResourceState = ResourceState.SUCCESS

    whenever(savePembayaranHutang.invoke(actualPembayaranHutang))
      .thenReturn(flow { emit(actualResourceState) })

    detailDebtViewModel.savePembayaranHutang(actualPembayaranHutang).test {
      val expectedVal = awaitItem()
      assertEquals(actualResourceState, expectedVal)
      awaitComplete()
    }
  }

  @Test
  fun `GetHutangByIdWithFlow, should be success`() = runTest {
    val actualHutang = HutangDummy.getAllHutang()[0].toModel()
    val actualId = actualHutang.uuid

    whenever(findHutangByIdWithFlow.invoke(actualId))
      .thenReturn(flow { emit(actualHutang) })

    detailDebtViewModel.getHutangByIdWithFlow(actualId).test {
      val expectedVal = awaitItem()
      assertEquals(expectedVal, actualHutang)
      assertEquals(expectedVal.uuid, actualHutang.uuid)
      awaitComplete()
    }
  }

  @Test
  fun `GetHutangById, should be success`() = runTest {
    val actualHutang = HutangDummy.getAllHutang()[0].toModel()
    val actualId = actualHutang.uuid

    whenever(findHutangById.invoke(actualId))
      .thenReturn(actualHutang)

    detailDebtViewModel.getHutangById(actualId)

    val expectedVal = detailDebtViewModel.debtModel.getOrAwaitValue()

    assertEquals(actualHutang, expectedVal)
    assertEquals(actualHutang.uuid, expectedVal.uuid)
  }

  @Test
  fun `GetAllAccount, should be success`() = runTest {
    val accounts = AkunDummy.getAllAccounts().map { it.toModel() }

    whenever(findAllAkun.invoke())
      .thenReturn(accounts)

    detailDebtViewModel.getAllAccount()

    val expectedVal = detailDebtViewModel.accounts.getOrAwaitValue()

    assertEquals(accounts, expectedVal)
    assertEquals(accounts.size, expectedVal.size)
  }

  @Test
  fun `SetState and EmptyState, should be success`() {
    val rvState = true
    val emptyState = false

    detailDebtViewModel.setState(rvState, emptyState)

    val expectedRvState = detailDebtViewModel.rvDebtRecordState.getOrAwaitValue()
    val expectedEmptyState = detailDebtViewModel.emptyListState.getOrAwaitValue()

    assertEquals(rvState, expectedRvState)
    assertEquals(emptyState, expectedEmptyState)
  }
}
