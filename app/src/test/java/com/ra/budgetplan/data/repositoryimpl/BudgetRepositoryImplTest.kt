package com.ra.budgetplan.data.repositoryimpl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.data.local.BudgetLocalDataSource
import com.ra.budgetplan.domain.entity.DetailBudget
import com.ra.budgetplan.domain.repository.BudgetRepository
import com.ra.budgetplan.dummy.model.BudgetDummy
import com.ra.budgetplan.dummy.model.KategoriDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class BudgetRepositoryImplTest {
  private lateinit var budgetRepository : BudgetRepository
  private val budgetLocalDataSource: BudgetLocalDataSource = mock()

  @Test
  fun `FindBudgetByDateAndKategoriId, should success`() = runBlocking {
    budgetRepository = BudgetRepositoryImpl(budgetLocalDataSource)

    val fromDate = LocalDate.now()
    val toDate = LocalDate.now()

    val actualBudget = BudgetDummy.getAllBudget()[0]
    val actualId = actualBudget.uuid

    whenever(budgetRepository.findBudgetByDateAndKategoriId(fromDate, toDate, actualId))
      .thenReturn(actualBudget)

    val expectedVal = budgetRepository.findBudgetByDateAndKategoriId(fromDate, toDate, actualId)

    verify(budgetLocalDataSource).findBudgetByDateAndKategoriId(fromDate, toDate, actualId)

    Assertions.assertEquals(actualId, expectedVal.uuid)
    Assertions.assertEquals(actualBudget, expectedVal)
  }

  @Test
  fun `FindAllByDate, should success`() = runBlocking {
    budgetRepository = BudgetRepositoryImpl(budgetLocalDataSource)

    val fromDate = LocalDate.now()
    val toDate = LocalDate.now()

    val actualListBudget = BudgetDummy.getAllBudget()
    val actualKategori = KategoriDummy.getAllKategori()[0]

    val detailBudget1 = DetailBudget(
      kategoriEntity = actualKategori,
      budget = actualListBudget[0]
    )

    val detailBudget2 = DetailBudget(
      kategoriEntity = actualKategori,
      budget = actualListBudget[1]
    )

    val actualListDetailBudget = listOf(detailBudget1, detailBudget2)

    whenever(budgetRepository.findAllByDate(fromDate, toDate))
      .thenReturn(actualListDetailBudget)

    val expectedVal = budgetRepository.findAllByDate(fromDate, toDate)

    verify(budgetLocalDataSource).findAllByDate(fromDate, toDate)

    Assertions.assertEquals(actualListDetailBudget, expectedVal)
    Assertions.assertEquals(actualListDetailBudget.size, expectedVal.size)
  }

  @Test
  fun `IsExistByDateAndKategoriId, should success`() = runBlocking {
    budgetRepository = BudgetRepositoryImpl(budgetLocalDataSource)

    val toDate = LocalDate.now()
    val fromDate = LocalDate.now()

    val actualBudget = BudgetDummy.getAllBudget()[0]
    val actualId = actualBudget.uuid

    whenever(budgetRepository.isExistByDateAndKategoriId(fromDate, toDate, actualId))
      .thenReturn(true)

    val expectedVal = budgetRepository.isExistByDateAndKategoriId(fromDate, toDate, actualId)

    verify(budgetLocalDataSource).isExistByDateAndKategoriId(fromDate, toDate, actualId)

    Assertions.assertEquals(expectedVal, true)
  }

  @Test
  fun `FindById, should success`() = runBlocking {
    budgetRepository = BudgetRepositoryImpl(budgetLocalDataSource)

    val actualBudget = BudgetDummy.getAllBudget()[0]
    val actualId = actualBudget.uuid

    whenever(budgetRepository.findById(actualId))
      .thenReturn(actualBudget)

    val expectedVal = budgetRepository.findById(actualId)

    verify(budgetLocalDataSource).findById(actualId)

    Assertions.assertEquals(actualBudget, expectedVal)
    Assertions.assertEquals(actualBudget.uuid, expectedVal.uuid)
  }

  @Test
  fun `Save, should success`() = runBlocking {
    budgetRepository = BudgetRepositoryImpl(budgetLocalDataSource)

    val actualBudget = BudgetDummy.getAllBudget()[0]

    whenever(budgetRepository.save(actualBudget))
      .thenReturn(Unit)

    budgetRepository.save(actualBudget)

    verify(budgetLocalDataSource).saveBudget(actualBudget)
  }

  @Test
  fun `Delete, should success`() = runBlocking {
    budgetRepository = BudgetRepositoryImpl(budgetLocalDataSource)

    val actualBudget = BudgetDummy.getAllBudget()[0]

    whenever(budgetRepository.delete(actualBudget))
      .thenReturn(Unit)

    budgetRepository.delete(actualBudget)

    verify(budgetLocalDataSource).deleteBudget(actualBudget)
  }

  @Test
  fun `Update, should success`() = runBlocking {
    budgetRepository = BudgetRepositoryImpl(budgetLocalDataSource)

    val actualBudget = BudgetDummy.getAllBudget()[0]

    whenever(budgetRepository.update(actualBudget))
      .thenReturn(Unit)

    budgetRepository.update(actualBudget)

    verify(budgetLocalDataSource).updateBudget(actualBudget)
  }
}