package com.ra.bkuang.domain.usecase.kategori.impl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.repository.KategoriRepository
import com.ra.bkuang.domain.usecase.kategori.DeleteKategori
import com.ra.bkuang.dummy.model.KategoriDummy
import com.ra.bkuang.domain.util.ResourceState
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
      assertEquals(ResourceState.SUCCESS, expectedVal)
      awaitComplete()
    }
  }
}