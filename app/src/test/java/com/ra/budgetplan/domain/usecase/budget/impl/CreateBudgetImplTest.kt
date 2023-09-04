package com.ra.budgetplan.domain.usecase.budget.impl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.repository.BudgetRepository
import com.ra.budgetplan.domain.repository.PengeluaranRepository
import com.ra.budgetplan.domain.usecase.budget.CreateBudget
import com.ra.budgetplan.dummy.model.BudgetDummy
import com.ra.budgetplan.dummy.model.PengeluaranDummy
import com.ra.budgetplan.util.StatusItem
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

internal class CreateBudgetImplTest {
  private lateinit var createBudget: CreateBudget
  private val budgetRepository: BudgetRepository = mock()
  private val pengeluaranRepository: PengeluaranRepository = mock()

  @Test
  fun `CreateBudget, should success`() = runBlocking {
    createBudget = CreateBudgetImpl(budgetRepository, pengeluaranRepository)

    val actualBudget = BudgetDummy.getAllBudget()[0]
    val actualPengeluaran = PengeluaranDummy.getTotalPengeluaran()

    whenever(budgetRepository.isExistByDateAndKategoriId(LocalDate.now(),  LocalDate.now(), actualBudget.idKategori))
      .thenReturn(false)

    whenever(pengeluaranRepository.getTotalPengeluaranByDateAndKategory(
      LocalDateTime.now(), LocalDateTime.now(), actualBudget.idKategori))
      .thenReturn(actualPengeluaran)

    whenever(budgetRepository.save(actualBudget))
      .thenReturn(Unit)

    val expectedVal = createBudget.invoke(LocalDate.now(), LocalDate.now(), actualBudget.toModel()).last()
    assertEquals(expectedVal, StatusItem.SUCCESS)
  }
}
