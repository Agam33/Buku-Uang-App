package com.ra.bkuang.data.local.datasource

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.data.local.database.dao.BudgetDao
import com.ra.bkuang.data.local.datasource.impl.BudgetLocalDataSourceImpl
import com.ra.bkuang.data.local.entity.DetailBudget
import com.ra.bkuang.dummy.model.BudgetDummy
import com.ra.bkuang.dummy.model.KategoriDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class BudgetLocalDataSourceImplTest {

  private lateinit var budgetLocalDataSource: BudgetLocalDataSource
  private val budgetDao: BudgetDao = mock()

  @Test
  fun `IsExistByDateAndKategoriId, should success`() = runBlocking {
    budgetLocalDataSource = BudgetLocalDataSourceImpl(budgetDao)

    val actualCategory = KategoriDummy.getAllKategori()[0]

    val fromDate = LocalDate.parse("2023-10-20")
    val toDate =  LocalDate.parse("2023-10-20")

    whenever(budgetLocalDataSource.isExistByDateAndKategoriId(fromDate, toDate, actualCategory.uuid))
      .thenReturn(true)

    val expectedVal = budgetLocalDataSource.isExistByDateAndKategoriId(fromDate, toDate, actualCategory.uuid)

    verify(budgetDao).isExistByDateAndKategoriId(fromDate, toDate, actualCategory.uuid)

    Assertions.assertEquals(expectedVal, true)
  }

  @Test
  fun `FindBudgetByDateAndKategoryId, should success`() = runBlocking {
    budgetLocalDataSource = BudgetLocalDataSourceImpl(budgetDao)

    val actualCategory = KategoriDummy.getAllKategori()[0]
    val actualBudget = BudgetDummy.getAllBudget()[0]

    val fromDate = LocalDate.parse("2023-10-20")
    val toDate =  LocalDate.parse("2023-10-20")

    whenever(budgetLocalDataSource.findBudgetByDateAndKategoriId(fromDate, toDate, actualCategory.uuid))
      .thenReturn(actualBudget)

    val expectedVal = budgetLocalDataSource.findBudgetByDateAndKategoriId(fromDate, toDate, actualCategory.uuid)

    verify(budgetDao).findBudgetByDateAndKategoriId(fromDate, toDate, actualCategory.uuid)

    Assertions.assertEquals(expectedVal, actualBudget)
    Assertions.assertEquals(expectedVal.uuid, actualBudget.uuid)
    Assertions.assertEquals(expectedVal.idKategori, actualBudget.idKategori)
  }

  @Test
  fun `FindAllByDate, should success`() = runBlocking {
    budgetLocalDataSource = BudgetLocalDataSourceImpl(budgetDao)

    val actualBudget = BudgetDummy.getAllBudget()
    val actualCategory = KategoriDummy.getAllKategori()

    val detailBudget1 = DetailBudget(
      budget = actualBudget[0],
      kategoriEntity = actualCategory[0]
    )

    val detailBudget2 = DetailBudget(
      budget = actualBudget[1],
      kategoriEntity = actualCategory[1]
    )

    val actualList = listOf(detailBudget1, detailBudget2)

    val fromDate = LocalDate.parse("2023-10-20")
    val toDate =  LocalDate.parse("2023-10-21")

    whenever(budgetLocalDataSource.findAllByDate(fromDate, toDate))
      .thenReturn(actualList)

    val expectedVal = budgetLocalDataSource.findAllByDate(fromDate, toDate)

    verify(budgetDao).findAllByDate(fromDate, toDate)

    Assertions.assertEquals(expectedVal, actualList)
    Assertions.assertEquals(expectedVal.size, actualList.size)
  }

  @Test
  fun `FindById, should success`() = runBlocking {
    budgetLocalDataSource = BudgetLocalDataSourceImpl(budgetDao)

    val actualBudget = BudgetDummy.getAllBudget()[0]
    val actualId = actualBudget.uuid

    whenever(budgetLocalDataSource.findById(actualId))
      .thenReturn(actualBudget)

    val expectedVal = budgetLocalDataSource.findById(actualId)

    verify(budgetDao).findById(actualId)

    Assertions.assertEquals(expectedVal, actualBudget)
    Assertions.assertEquals(expectedVal.uuid, actualBudget.uuid)
  }

  @Test
  fun `SaveBudget, should success`() = runBlocking {
    budgetLocalDataSource = BudgetLocalDataSourceImpl(budgetDao)

    val budget = BudgetDummy.getAllBudget()[0]

    whenever(budgetLocalDataSource.saveBudget(budget)).thenReturn(Unit)

    budgetLocalDataSource.saveBudget(budget)

    verify(budgetDao).save(budget)
  }

  @Test
  fun `DeleteBudget, should success`() = runBlocking {
    budgetLocalDataSource = BudgetLocalDataSourceImpl(budgetDao)

    val budget = BudgetDummy.getAllBudget()[0]

    whenever(budgetLocalDataSource.deleteBudget(budget)).thenReturn(Unit)

    budgetLocalDataSource.deleteBudget(budget)

    verify(budgetDao).delete(budget)
  }

  @Test
  fun `UpdateBudget, should success`() = runBlocking {
    budgetLocalDataSource = BudgetLocalDataSourceImpl(budgetDao)

    val budget = BudgetDummy.getAllBudget()[0]

    whenever(budgetLocalDataSource.updateBudget(budget)).thenReturn(Unit)

    budgetLocalDataSource.updateBudget(budget)

    verify(budgetDao).update(budget)
  }
}