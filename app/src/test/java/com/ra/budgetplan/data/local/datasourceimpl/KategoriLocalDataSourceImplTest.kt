package com.ra.budgetplan.data.local.datasourceimpl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.data.local.KategoriLocalDataSource
import com.ra.budgetplan.data.local.database.dao.KategoriDao
import com.ra.budgetplan.domain.entity.TipeKategori
import com.ra.budgetplan.dummy.model.KategoriDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class KategoriLocalDataSourceImplTest {

  private lateinit var kategoriLocalDataSource: KategoriLocalDataSource
  private val kategoriDao: KategoriDao = mock()

  @Test
  fun `FindByType, should success`() = runBlocking {
    kategoriLocalDataSource = KategoriLocalDataSourceImpl(kategoriDao)

    val actualKategori = KategoriDummy.getAllKategori()
    val flow = flow { emit(actualKategori) }

    whenever(kategoriLocalDataSource.findByType(TipeKategori.PENGELUARAN))
      .thenReturn(flow)

    kategoriLocalDataSource.findByType(TipeKategori.PENGELUARAN).test {
      val expectedVal = expectItem()
      assertEquals(expectedVal, actualKategori)
      assertEquals(expectedVal.size, actualKategori.size)
      expectComplete()
    }
  }

  @Test
  fun `SaveKategori, should success`() = runBlocking {
    kategoriLocalDataSource = KategoriLocalDataSourceImpl(kategoriDao)

    val actualKategori = KategoriDummy.getAllKategori()[0]

    whenever(kategoriLocalDataSource.saveKategori(actualKategori)).thenReturn(Unit)

    kategoriLocalDataSource.saveKategori(actualKategori)

    verify(kategoriDao).save(actualKategori)
  }

  @Test
  fun `DeleteKategori, should success`() = runBlocking {
    kategoriLocalDataSource = KategoriLocalDataSourceImpl(kategoriDao)

    val actualKategori = KategoriDummy.getAllKategori()[0]

    whenever(kategoriLocalDataSource.deleteKategori(actualKategori)).thenReturn(Unit)

    kategoriLocalDataSource.deleteKategori(actualKategori)

    verify(kategoriDao).delete(actualKategori)

  }

  @Test
  fun `UpdateKategori, should success`() = runBlocking {
    kategoriLocalDataSource = KategoriLocalDataSourceImpl(kategoriDao)

    val actualKategori = KategoriDummy.getAllKategori()[0]

    whenever(kategoriLocalDataSource.updateKategori(actualKategori)).thenReturn(Unit)

    kategoriLocalDataSource.updateKategori(actualKategori)

    verify(kategoriDao).update(actualKategori)
  }

  @Test
  fun `FindAllKategori, should success`() = runBlocking {
    kategoriLocalDataSource = KategoriLocalDataSourceImpl(kategoriDao)

    val actualListKategori = KategoriDummy.getAllKategori()
    val flow = flow { emit(actualListKategori) }

    whenever(kategoriLocalDataSource.findAllKategori()).thenReturn(flow)

    kategoriLocalDataSource.findAllKategori().test {
      val expectedVal = expectItem()
      assertEquals(actualListKategori, expectedVal)
      assertEquals(actualListKategori.size, expectedVal.size)
      expectComplete()
    }

  }

  @Test
  fun `FindKategoriById, should success`() = runBlocking {
    kategoriLocalDataSource = KategoriLocalDataSourceImpl(kategoriDao)

    val actualKategori = KategoriDummy.getAllKategori()[0]
    val flow = flow { emit(actualKategori) }

    whenever(kategoriLocalDataSource.findKategoriById(actualKategori.uuid)).thenReturn(flow)

    kategoriLocalDataSource.findKategoriById(actualKategori.uuid).test {
      val expectedVal = expectItem()
      assertEquals(expectedVal, actualKategori)
      assertEquals(expectedVal.uuid, actualKategori.uuid)
      expectComplete()
    }
  }
}