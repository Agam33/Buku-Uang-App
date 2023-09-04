package com.ra.budgetplan.domain.usecase.budget.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.entity.DetailBudget
import com.ra.budgetplan.domain.repository.BudgetRepository
import com.ra.budgetplan.domain.usecase.budget.FindAllBudgetByDate
import com.ra.budgetplan.dummy.model.BudgetDummy
import com.ra.budgetplan.dummy.model.KategoriDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class FindAllBudgetByDateImplTest {
  private lateinit var findAllBudgetByDate: FindAllBudgetByDate
  private val budgetRepository: BudgetRepository = mock()

  @Test
  fun `FindAllBudgetByDate, should success`() = runBlocking {
    findAllBudgetByDate = FindAllBudgetByDateImpl(budgetRepository)

    val fromDate = LocalDate.now()
    val toDate = LocalDate.now()

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

    whenever(budgetRepository.findAllByDate(fromDate, toDate))
      .thenReturn(listDetailBudget)

    val expectedVal = findAllBudgetByDate.invoke(fromDate, toDate)

    assertEquals(listDetailBudget.size, expectedVal.size)
  }
}