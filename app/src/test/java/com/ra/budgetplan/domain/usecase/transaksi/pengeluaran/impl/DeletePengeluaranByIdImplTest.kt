package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.BudgetRepository
import com.ra.budgetplan.domain.repository.PengeluaranRepository
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.DeletePengeluaranById
import com.ra.budgetplan.dummy.model.AkunDummy
import com.ra.budgetplan.dummy.model.BudgetDummy
import com.ra.budgetplan.dummy.model.PengeluaranDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DeletePengeluaranByIdImplTest {
  private lateinit var deletePengeluaranById: DeletePengeluaranById
  private val pengeluaranRepository: PengeluaranRepository = mock()
  private val akunRepository: AkunRepository = mock()
  private val budgetRepository: BudgetRepository = mock()

  @Test
  fun `DeletePengeluaranById, should success`() = runBlocking {
    deletePengeluaranById = DeletePengeluaranByIdImpl(pengeluaranRepository, akunRepository, budgetRepository)

    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]
    val actualId = actualPengeluaran.uuid
    val actualBudget = BudgetDummy.getAllBudget()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]
    val fromDate = actualPengeluaran.updatedAt.toLocalDate().withDayOfMonth(1)
    val toDate = actualPengeluaran.updatedAt.toLocalDate().withDayOfMonth(actualPengeluaran.updatedAt.toLocalDate().dayOfMonth)

    whenever(pengeluaranRepository.findById(actualId)).thenReturn(actualPengeluaran)
    whenever(pengeluaranRepository.delete(actualPengeluaran)).thenReturn(Unit)
    whenever(akunRepository.findById(actualPengeluaran.idAkun)).thenReturn(actualAkun)
    whenever(budgetRepository.isExistByDateAndKategoriId(fromDate, toDate, actualPengeluaran.idKategori))
      .thenReturn(true)
    whenever(budgetRepository.findBudgetByDateAndKategoriId(fromDate, toDate, actualPengeluaran.idKategori))
      .thenReturn(actualBudget)
    whenever(budgetRepository.update(actualBudget)).thenReturn(Unit)
    whenever(akunRepository.update(actualAkun)).thenReturn(Unit)

    val expectedVal = deletePengeluaranById.invoke(actualId)
    assertEquals(expectedVal, Unit)
  }
}