package com.ra.bkuang.domain.usecase.kategori.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.repository.KategoriRepository
import com.ra.bkuang.domain.usecase.kategori.UpdateKategori
import com.ra.bkuang.dummy.model.KategoriDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class UpdateKategoriImplTest {
  private val repository: KategoriRepository = mock()
  private lateinit var updateKategori: UpdateKategori

  @Test
  fun `UpdateKategori, should success`() = runBlocking {
    updateKategori = UpdateKategoriImpl(repository)

    val actualKategori = KategoriDummy.getAllKategori()[0]

    whenever(repository.update(actualKategori))
      .thenReturn(Unit)

    updateKategori.invoke(actualKategori.toModel())

    verify(repository).update(actualKategori)
  }
}