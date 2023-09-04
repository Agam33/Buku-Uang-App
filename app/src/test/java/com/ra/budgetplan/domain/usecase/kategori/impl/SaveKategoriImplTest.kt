package com.ra.budgetplan.domain.usecase.kategori.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.repository.KategoriRepository
import com.ra.budgetplan.domain.usecase.kategori.SaveKategori
import com.ra.budgetplan.dummy.model.KategoriDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SaveKategoriImplTest {
  private val repository: KategoriRepository = mock()
  private lateinit var saveKategori: SaveKategori

  @Test
  fun `SaveKategori, should success`() = runBlocking {
    saveKategori = SaveKategoriImpl(repository)

    val actualKategori = KategoriDummy.getAllKategori()[0]

    whenever(repository.save(actualKategori))
      .thenReturn(Unit)

    saveKategori.invoke(actualKategori.toModel())

    verify(repository).save(actualKategori)
  }
}