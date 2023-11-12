package com.ra.bkuang.domain.usecase.akun.impl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.data.local.entity.TipeKategori
import com.ra.bkuang.domain.repository.KategoriRepository
import com.ra.bkuang.domain.usecase.akun.FindCategoryByType
import com.ra.bkuang.dummy.model.KategoriDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class FindCategoryByTypeImplTest {
  private lateinit var findCategoryByType: FindCategoryByType
  private val repository: KategoriRepository = mock()

  @Test
  fun `FindCategoryByType - Method 1, should success`() = runBlocking {
    findCategoryByType = FindCategoryByTypeImpl(repository)

    val actualListKategori = KategoriDummy.getAllKategori()
    val flow = flow { emit(actualListKategori) }

    whenever(repository.findByType(TipeKategori.PENGELUARAN))
      .thenReturn(flow)

    val expectedVal = findCategoryByType.invoke(TipeKategori.PENGELUARAN).last()

    assertEquals(actualListKategori.size, expectedVal.data?.size)
  }

  @Test
  fun `FindCategoryByType - Method 2, should success`() = runBlocking {
    findCategoryByType = FindCategoryByTypeImpl(repository)

    val actualListKategoriPengeluaran = KategoriDummy.getAllKategori().slice(0..1)
    val actualListKategoriPendapatan = KategoriDummy.getAllKategori().slice(2..3)

    whenever(repository.findByType(TipeKategori.PENGELUARAN))
      .thenReturn(flow { emit(actualListKategoriPengeluaran) })
    whenever(repository.findByType(TipeKategori.PENDAPATAN))
      .thenReturn(flow { emit(actualListKategoriPendapatan) })

    findCategoryByType.invoke().test {
      val expectedVal = awaitItem()
      assertEquals(2, expectedVal.size)
      awaitComplete()
    }

  }

}