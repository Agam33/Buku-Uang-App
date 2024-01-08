package com.ra.bkuang.domain.usecase.budget.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.repository.BudgetRepository
import com.ra.bkuang.domain.repository.PengeluaranRepository
import com.ra.bkuang.domain.usecase.budget.CreateBudget
import com.ra.bkuang.dummy.model.BudgetDummy
import com.ra.bkuang.dummy.model.PengeluaranDummy
import com.ra.bkuang.domain.util.ResourceState
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
    assertEquals(expectedVal, ResourceState.SUCCESS)
  }
}