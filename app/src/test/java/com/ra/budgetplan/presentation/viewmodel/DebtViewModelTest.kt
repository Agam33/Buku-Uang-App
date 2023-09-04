package com.ra.budgetplan.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.usecase.hutang.CreateHutang
import com.ra.budgetplan.domain.usecase.hutang.DeleteHutang
import com.ra.budgetplan.domain.usecase.hutang.FindHutangById
import com.ra.budgetplan.domain.usecase.hutang.ShowAllHutang
import com.ra.budgetplan.domain.usecase.hutang.UpdateHutang
import com.ra.budgetplan.dummy.model.HutangDummy
import com.ra.budgetplan.util.MainDispatcherRule
import com.ra.budgetplan.util.Resource
import com.ra.budgetplan.util.StatusItem
import com.ra.budgetplan.util.getOrAwaitValue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.rules.TestRule

class DebtViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Rule
  @JvmField
  var rule: TestRule = InstantTaskExecutorRule()

  private lateinit var debtViewModel: DebtViewModel
  private val createHutang: CreateHutang = mock()
  private val updateHutang: UpdateHutang = mock()
  private val showAllHutang: ShowAllHutang = mock()
  private val deleteHutang: DeleteHutang = mock()
  private val findHutangById: FindHutangById = mock()

  @Before
  fun init() {
    debtViewModel = DebtViewModel(
      createHutang = createHutang,
      updateHutang = updateHutang,
      showAllHutang = showAllHutang,
      deleteHutang = deleteHutang,
      findHutangById = findHutangById
    )
  }

  @Test
  fun `UpdateHutang, should be success`() = runTest {
    val actualHutang = HutangDummy.getAllHutang()[0].toModel()
    val actualStatus = StatusItem.SUCCESS

    whenever(updateHutang.invoke(actualHutang))
      .thenReturn(flow { emit(actualStatus) })

    debtViewModel.updateHutang(actualHutang).test {
      val expectedVal = awaitItem()
      assertEquals(actualStatus, expectedVal)
      awaitComplete()
    }
  }

  @Test
  fun `DeleteHutang, should be success`() = runTest {
    val actualHutang = HutangDummy.getAllHutang()[0].toModel()
    val actualStatus = StatusItem.SUCCESS

    whenever(deleteHutang.invoke(actualHutang))
      .thenReturn(flow { emit(actualStatus) })

    debtViewModel.deleteHutang(actualHutang).test {
      val expectedVal = awaitItem()
      assertEquals(actualStatus, expectedVal)
      awaitComplete()
    }
  }

  @Test
  fun `CreateHutang, should be success`() = runTest {
    val actualHutang = HutangDummy.getAllHutang()[0].toModel()
    val actualStatus = StatusItem.SUCCESS

    whenever(createHutang.invoke(actualHutang))
      .thenReturn(flow { emit(actualStatus) })

    debtViewModel.createHutang(actualHutang).test {
      val expectedVal = awaitItem()
      assertEquals(actualStatus, expectedVal)
      awaitComplete()
    }
  }

  @Test
  fun `GetHutangById, should be success`() = runTest {
    val actualHutang = HutangDummy.getAllHutang()[0].toModel()
    val actualId = actualHutang.uuid

    whenever(findHutangById.invoke(actualId))
      .thenReturn(actualHutang)

    debtViewModel.getHutangById(actualId)

    val expectedVal = debtViewModel.hutangModel.getOrAwaitValue()

    assertEquals(expectedVal.uuid, actualHutang.uuid)
  }

  @Test
  fun `GetHutangList, should be success`() = runTest {
    val actualListHutang = HutangDummy.getAllHutang().map { it.toModel() }

    val resource = Resource.Success(actualListHutang)

    whenever(showAllHutang.invoke())
      .thenReturn(resource)

    debtViewModel.getHutangList()

    val expectedVal = debtViewModel.hutangList.getOrAwaitValue()

    assertEquals(resource.data, expectedVal.data)
    assertEquals(resource.data?.size, expectedVal.data?.size)
  }

  @Test
  fun `SetState, should be success`() {
    val rvState = true
    val emptyState = false

    debtViewModel.setState(rvState, emptyState)

    val expectedRvState = debtViewModel.rvDebtListState.getOrAwaitValue()
    val expectedEmptyState = debtViewModel.emptyListState.getOrAwaitValue()

    assertEquals(rvState, expectedRvState)
    assertEquals(emptyState, expectedEmptyState)
  }
}
