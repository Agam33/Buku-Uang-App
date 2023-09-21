package com.ra.budgetplan.domain.usecase.kategori.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.repository.KategoriRepository
import com.ra.budgetplan.domain.usecase.kategori.FindKategoriById
import com.ra.budgetplan.dummy.model.KategoriDummy
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class FindKategoriByIdImplTest {
  private val repository: KategoriRepository = mock()
  private lateinit var findKategoriById: FindKategoriById

  @Test
  fun `FindKategoriById, should success`() = runBlocking {
    findKategoriById = FindKategoriByIdImpl(repository)

    val actualKategori = KategoriDummy.getAllKategori()[0]
    val actualId = actualKategori.uuid

    whenever(repository.findById(actualId))
      .thenReturn(flow { emit(actualKategori) })

    val expectedVal = findKategoriById.invoke(actualId).first()

    assertEquals(actualId, expectedVal.uuid)
  }
}