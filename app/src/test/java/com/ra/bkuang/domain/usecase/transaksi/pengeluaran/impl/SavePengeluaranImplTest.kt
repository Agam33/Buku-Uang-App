package com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.repository.BudgetRepository
import com.ra.bkuang.domain.repository.PengeluaranRepository
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.SavePengeluaran
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.BudgetDummy
import com.ra.bkuang.dummy.model.PengeluaranDummy
import com.ra.bkuang.domain.util.ResourceState
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SavePengeluaranImplTest {
  private lateinit var savePengeluaran: SavePengeluaran
  private val pengeluaranRepository: PengeluaranRepository = mock()
  private val akunRepository: AkunRepository = mock()
  private val budgetRepository: BudgetRepository = mock()

  @Test
  fun `SavePengeluaran, should success`() = runBlocking {
    savePengeluaran = SavePengeluaranImpl(pengeluaranRepository, akunRepository, budgetRepository)

    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]
    val actualBudget = BudgetDummy.getAllBudget()[0]
    val fromDate = actualPengeluaran.updatedAt.toLocalDate().withDayOfMonth(1)
    val toDate = actualPengeluaran.updatedAt.toLocalDate().withDayOfMonth(actualPengeluaran.updatedAt.toLocalDate().dayOfMonth)
    val katId = actualPengeluaran.idKategori

    whenever(akunRepository.findById(actualPengeluaran.idAkun)).thenReturn(actualAkun)
    whenever(pengeluaranRepository.save(actualPengeluaran)).thenReturn(Unit)
    whenever(budgetRepository.isExistByDateAndKategoriId(fromDate, toDate, katId)).thenReturn(true)
    whenever(budgetRepository.findBudgetByDateAndKategoriId(fromDate, toDate, katId)).thenReturn(actualBudget)
    whenever(budgetRepository.update(actualBudget)).thenReturn(Unit)
    whenever(akunRepository.update(actualAkun)).thenReturn(Unit)

    val expectedVal = savePengeluaran.invoke(actualPengeluaran.toModel())
    assertEquals(expectedVal, ResourceState.SUCCESS)
  }
}