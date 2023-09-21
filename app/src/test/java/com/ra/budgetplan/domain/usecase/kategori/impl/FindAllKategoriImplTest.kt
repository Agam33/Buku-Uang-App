package com.ra.budgetplan.domain.usecase.kategori.impl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.repository.KategoriRepository
import com.ra.budgetplan.domain.usecase.kategori.FindAllKategori
import com.ra.budgetplan.dummy.model.KategoriDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class FindAllKategoriImplTest {
  private lateinit var findAllKategori: FindAllKategori
  private val repository: KategoriRepository = mock()

  @Test
  fun `DeleteKategori, should success`() = runBlocking {
    findAllKategori = FindAllKategoriImpl(repository)

    val actualKategori = KategoriDummy.getAllKategori()

    whenever(repository.findAll())
      .thenReturn(flow { emit(actualKategori) })

    findAllKategori.invoke().test {
      val expectedVal = awaitItem()
      assertEquals(actualKategori.size, expectedVal.data?.size)
      awaitComplete()
    }
  }
}