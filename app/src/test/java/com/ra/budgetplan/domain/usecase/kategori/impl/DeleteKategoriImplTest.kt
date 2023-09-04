package com.ra.budgetplan.domain.usecase.kategori.impl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.repository.KategoriRepository
import com.ra.budgetplan.domain.usecase.kategori.DeleteKategori
import com.ra.budgetplan.dummy.model.KategoriDummy
import com.ra.budgetplan.util.StatusItem
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DeleteKategoriImplTest {
  private val repository: KategoriRepository = mock()
  private lateinit var deleteKategori: DeleteKategori

  @Test
  fun `DeleteKategori, should success`() = runBlocking {
    deleteKategori = DeleteKategoriImpl(repository)

    val actualKategori = KategoriDummy.getAllKategori()[0]

    whenever(repository.delete(actualKategori))
      .thenReturn(Unit)

    deleteKategori.invoke(actualKategori.toModel()).test {
      awaitItem()
      val expectedVal = awaitItem()
      assertEquals(StatusItem.SUCCESS, expectedVal)
      awaitComplete()
    }
  }
}