package com.ra.bkuang.domain.usecase.kategori.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.repository.KategoriRepository
import com.ra.bkuang.domain.usecase.kategori.SaveKategori
import com.ra.bkuang.dummy.model.KategoriDummy
import kotlinx.coroutines.runBlocking
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