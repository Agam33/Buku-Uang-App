package com.ra.budgetplan.domain.usecase.budget.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.repository.BudgetRepository
import com.ra.budgetplan.domain.usecase.budget.FindBudgetById
import com.ra.budgetplan.dummy.model.BudgetDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class FindBudgetByIdImplTest {
  private lateinit var findBudgetById: FindBudgetById
  private val budgetRepository: BudgetRepository = mock()

  @Test
  fun `FindBudgetById, should success`() = runBlocking {
    findBudgetById  = FindBudgetByIdImpl(budgetRepository)

    val actualBudget = BudgetDummy.getAllBudget()[0]
    val actualId = actualBudget.uuid

    whenever(budgetRepository.findById(actualId))
      .thenReturn(actualBudget)

    val expectedVal = findBudgetById.invoke(actualId)

    Assertions.assertEquals(actualBudget.uuid, expectedVal.uuid)
  }
}