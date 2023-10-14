package com.ra.budgetplan.domain.usecase.budget.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.repository.BudgetRepository
import com.ra.budgetplan.domain.repository.PengeluaranRepository
import com.ra.budgetplan.domain.usecase.budget.EditBudget
import com.ra.budgetplan.dummy.model.BudgetDummy
import com.ra.budgetplan.dummy.model.PengeluaranDummy
import com.ra.budgetplan.util.ResourceState
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class EditBudgetImplTest {
  private lateinit var editBudget: EditBudget
  private val budgetRepository: BudgetRepository = mock()
  private val pengeluaranRepository: PengeluaranRepository = mock()

  @Test
  fun `EditBudget, should success`() = runBlocking {
    editBudget = EditBudgetImpl(budgetRepository, pengeluaranRepository)

    val actualBudget = BudgetDummy.getAllBudget()[0]
    val actualTotalPengeluaran = PengeluaranDummy.getTotalPengeluaran()

    whenever(pengeluaranRepository.getTotalPengeluaranByDateAndKategory(
      LocalDateTime.now(), LocalDateTime.now(), actualBudget.idKategori))
      .thenReturn(actualTotalPengeluaran)

    whenever(budgetRepository.update(actualBudget))
      .thenReturn(Unit)

    val expectedVal = editBudget.invoke(actualBudget.toModel()).last()

    assertEquals(ResourceState.SUCCESS, expectedVal)
  }
}