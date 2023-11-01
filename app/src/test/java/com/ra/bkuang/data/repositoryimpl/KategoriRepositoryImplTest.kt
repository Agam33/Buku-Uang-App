package com.ra.bkuang.data.repositoryimpl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.data.local.KategoriLocalDataSource
import com.ra.bkuang.data.entity.TipeKategori
import com.ra.bkuang.domain.repository.KategoriRepository
import com.ra.bkuang.dummy.model.KategoriDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class KategoriRepositoryImplTest {
  private lateinit var kategoriRepository: KategoriRepository
  private val kategoriLocalDataSource: KategoriLocalDataSource = mock()

  @Test
  fun `FindByType, should success`() = runBlocking {
    kategoriRepository = KategoriRepositoryImpl(kategoriLocalDataSource)

    val actualListKategori = KategoriDummy.getAllKategori()
    val flow = flow { emit(actualListKategori) }

    whenever(kategoriRepository.findByType(TipeKategori.PENGELUARAN))
      .thenReturn(flow)

    kategoriRepository.findByType(TipeKategori.PENGELUARAN).test {
      val expectedVal = awaitItem()
      assertEquals(actualListKategori, expectedVal)
      assertEquals(actualListKategori.size, expectedVal.size)
      awaitComplete()
    }
  }

  @Test
  fun `Save, should success`() = runBlocking {
    kategoriRepository = KategoriRepositoryImpl(kategoriLocalDataSource)

    val actualKategori = KategoriDummy.getAllKategori()[0]

    whenever(kategoriRepository.save(actualKategori))
      .thenReturn(Unit)

    kategoriRepository.save(actualKategori)

    verify(kategoriLocalDataSource).saveKategori(actualKategori)
  }

  @Test
  fun `Delete, should success`() = runBlocking {
    kategoriRepository = KategoriRepositoryImpl(kategoriLocalDataSource)

    val actualKategori = KategoriDummy.getAllKategori()[0]

    whenever(kategoriRepository.delete(actualKategori))
      .thenReturn(Unit)

    kategoriRepository.delete(actualKategori)

    verify(kategoriLocalDataSource).deleteKategori(actualKategori)
  }

  @Test
  fun `Update, should success`() = runBlocking {
    kategoriRepository = KategoriRepositoryImpl(kategoriLocalDataSource)

    val actualKategori = KategoriDummy.getAllKategori()[0]

    whenever(kategoriRepository.update(actualKategori))
      .thenReturn(Unit)

    kategoriRepository.update(actualKategori)

    verify(kategoriLocalDataSource).updateKategori(actualKategori)
  }

  @Test
  fun `FindAll, should success`() = runBlocking {
    kategoriRepository = KategoriRepositoryImpl(kategoriLocalDataSource)

    val actualListKategori = KategoriDummy.getAllKategori()
    val flow = flow { emit(actualListKategori) }

    whenever(kategoriRepository.findAll())
      .thenReturn(flow)

    kategoriRepository.findAll().test {
      val expectedVal = awaitItem()
      assertEquals(expectedVal, actualListKategori)
      assertEquals(expectedVal.size, actualListKategori.size)
      awaitComplete()
    }
  }

  @Test
  fun `FindById, should success`() = runBlocking {
    kategoriRepository = KategoriRepositoryImpl(kategoriLocalDataSource)

    val actualKategori = KategoriDummy.getAllKategori()[0]
    val actualId = actualKategori.uuid
    val flow = flow { emit(actualKategori) }

    whenever(kategoriRepository.findById(actualId))
      .thenReturn(flow)

    kategoriRepository.findById(actualId).test {
      val expectedVal = awaitItem()
      assertEquals(expectedVal, actualKategori)
      assertEquals(expectedVal.uuid, actualKategori.uuid)
      awaitComplete()
    }
  }
}