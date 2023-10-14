package com.ra.budgetplan.domain.usecase.budget.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.repository.BudgetRepository
import com.ra.budgetplan.domain.usecase.budget.DeleteBudgetById
import com.ra.budgetplan.dummy.model.BudgetDummy
import com.ra.budgetplan.util.ResourceState
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DeleteBudgetByIdImplTest {
  private lateinit var deleteBudgetById: DeleteBudgetById
  private val budgetRepository: BudgetRepository = mock()

  @Test
  fun `DeleteBudgetById, should success`() = runBlocking {
    deleteBudgetById = DeleteBudgetByIdImpl(budgetRepository)

    val actualBudget = BudgetDummy.getAllBudget()[0]

    whenever(budgetRepository.findById(actualBudget.uuid))
      .thenReturn(actualBudget)

    whenever(budgetRepository.delete(actualBudget))
      .thenReturn(Unit)

    val expectedVal = deleteBudgetById.invoke(actualBudget.uuid).last()

    assertEquals(ResourceState.SUCCESS, expectedVal)
  }
}