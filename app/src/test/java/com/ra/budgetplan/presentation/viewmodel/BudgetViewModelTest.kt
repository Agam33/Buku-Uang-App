package com.ra.budgetplan.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.entity.DetailBudget
import com.ra.budgetplan.domain.entity.TipeKategori
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.usecase.akun.FindCategoryByType
import com.ra.budgetplan.domain.usecase.budget.CreateBudget
import com.ra.budgetplan.domain.usecase.budget.DeleteBudgetById
import com.ra.budgetplan.domain.usecase.budget.EditBudget
import com.ra.budgetplan.domain.usecase.budget.FindAllBudgetByDate
import com.ra.budgetplan.domain.usecase.budget.FindBudgetById
import com.ra.budgetplan.dummy.model.BudgetDummy
import com.ra.budgetplan.dummy.model.KategoriDummy
import com.ra.budgetplan.util.MainDispatcherRule
import com.ra.budgetplan.util.Resource
import com.ra.budgetplan.util.StatusItem
import com.ra.budgetplan.util.getOrAwaitValue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.rules.TestRule
import java.time.LocalDate

class BudgetViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Rule
  @JvmField
  var rule: TestRule = InstantTaskExecutorRule()

  private lateinit var budgetViewModel: BudgetViewModel
  private val findAllBudgetByDate: FindAllBudgetByDate = mock()
  private val editBudget: EditBudget = mock()
  private val deleteBudgetById: DeleteBudgetById = mock()
  private val createBudget: CreateBudget = mock()
  private val findKategoriByType: FindCategoryByType = mock()
  private val findBudgetById: FindBudgetById = mock()

  @Before
  fun init() {
    budgetViewModel = BudgetViewModel(
      findAllBudgetByDate,
      editBudget,
      deleteBudgetById,
      createBudget,
      findKategoriByType,
      findBudgetById
    )
  }

  @Test
  fun `CreateBudget, should be success`() = runTest {
    val budget = BudgetDummy.getAllBudget()[0].toModel()

    val actualStatusItem = StatusItem.SUCCESS
    whenever(createBudget.invoke(budget.bulanTahun, budget.bulanTahun, budget))
      .thenReturn(flow { emit(actualStatusItem) })

    budgetViewModel.createBudget(budget).test {
      val expectedVal = awaitItem()
      assertEquals(actualStatusItem, expectedVal)
      awaitComplete()
    }
  }

  @Test
  fun `UpdateBudget, should be success`() = runTest {
    val budget = BudgetDummy.getAllBudget()[0].toModel()

    val actualStatusItem = StatusItem.SUCCESS
    whenever(editBudget.invoke(budget))
      .thenReturn(flow { emit(actualStatusItem) })

    budgetViewModel.updateBudget(budget).test {
      val expectedVal = awaitItem()
      assertEquals(actualStatusItem, expectedVal)
      awaitComplete()
    }
  }

  @Test
  fun `DeleteBudgetById, should be success`() = runTest {
    val budget = BudgetDummy.getAllBudget()[0].toModel()

    val actualStatus = StatusItem.SUCCESS
    whenever(deleteBudgetById.invoke(budget.uuid))
      .thenReturn(flow { emit(actualStatus) })

    budgetViewModel.deleteBudgetById(budget.uuid).test {
      val expectedVal = awaitItem()
      assertEquals(actualStatus, expectedVal)
      awaitComplete()
    }
  }

  @Test
  fun `SetCategoryByType, should be success`() {
    val listKategori = KategoriDummy.getAllKategori().map { it.toModel() }

    val tipeKategori = TipeKategori.PENGELUARAN
    val resource = Resource.Success(listKategori)
    whenever(findKategoriByType.invoke(tipeKategori))
      .thenReturn(flow { emit(resource) })

    budgetViewModel.setCategoryByType(tipeKategori)

    val expectedVal = budgetViewModel.listCategoryByType.getOrAwaitValue()

    assertEquals(expectedVal.size, listKategori.size)
  }

  @Test
  fun `FindAllBudget, should be success`() = runTest {
    val budgetList = BudgetDummy.getAllBudget()
    val kategori = KategoriDummy.getAllKategori()[0]

    val budgetDetail1 = DetailBudget(
      kategoriEntity = kategori,
      budget = budgetList[0]
    )

    val budgetDetail2 = DetailBudget(
      kategoriEntity = kategori,
      budget = budgetList[1]
    )

    val listDetailBudget = listOf(budgetDetail1, budgetDetail2)
    val fromDate = LocalDate.now()
    val toDate = LocalDate.now()

    whenever(findAllBudgetByDate.invoke(fromDate, toDate))
      .thenReturn(listDetailBudget)

    budgetViewModel.findAllBudget(fromDate, toDate)

    val expectedVal = budgetViewModel.listBudget.getOrAwaitValue()

    assertEquals(expectedVal.size, listDetailBudget.size)
  }

  @Test
  fun `FindBudgetById, should be success`() = runTest {
    val budget = BudgetDummy.getAllBudget()[0].toModel()
    val budgetId = budget.uuid

    whenever(findBudgetById.invoke(budgetId))
      .thenReturn(budget)

    budgetViewModel.findBudgetById(budgetId)

    val expectedVal = budgetViewModel.budgetModel.getOrAwaitValue()

    assertEquals(expectedVal.uuid, budget.uuid)
  }
}