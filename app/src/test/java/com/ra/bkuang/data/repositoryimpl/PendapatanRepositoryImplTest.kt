package com.ra.bkuang.data.repositoryimpl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.data.local.PengeluaranLocalDataSource
import com.ra.bkuang.data.local.entity.DetailPengeluaran
import com.ra.bkuang.domain.repository.PengeluaranRepository
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.KategoriDummy
import com.ra.bkuang.dummy.model.PengeluaranDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class PendapatanRepositoryImplTest {
  private lateinit var pengeluaranRepository: PengeluaranRepository
  private val pengeluaranLocalDataSource: PengeluaranLocalDataSource = mock()

  @Test
  fun `GetTotalPengeluaranByDateAndKategory, should success`() = runBlocking {
    pengeluaranRepository = PengeluaranRepositoryImpl(pengeluaranLocalDataSource)

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    val actualTotal = PengeluaranDummy.getTotalPengeluaran()
    val actualKategoriId = KategoriDummy.getAllKategori()[0].uuid

    whenever(pengeluaranRepository.getTotalPengeluaranByDateAndKategory(fromDate, toDate, actualKategoriId))
      .thenReturn(actualTotal)

    val expectedVal = pengeluaranRepository.getTotalPengeluaranByDateAndKategory(fromDate, toDate, actualKategoriId)

    verify(pengeluaranLocalDataSource).getTotalPengeluaranByDateAndKategory(fromDate, toDate, actualKategoriId)

    assertEquals(actualTotal, expectedVal)
  }

  @Test
  fun `GetTotalPengeluaran, should success`() = runBlocking {
    pengeluaranRepository = PengeluaranRepositoryImpl(pengeluaranLocalDataSource)

    val actualTotalPengeluaran = PengeluaranDummy.getTotalPengeluaran()
    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    whenever(pengeluaranRepository.getTotalPengeluaran(fromDate, toDate))
      .thenReturn(actualTotalPengeluaran)

    val expectedVal = pengeluaranRepository.getTotalPengeluaran(fromDate, toDate)

    verify(pengeluaranLocalDataSource).getTotalPengeluaran(fromDate, toDate)

    assertEquals(actualTotalPengeluaran, expectedVal)
  }

  @Test
  fun `FindById, should success`() = runBlocking {
    pengeluaranRepository = PengeluaranRepositoryImpl(pengeluaranLocalDataSource)

    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]
    val actualId = actualPengeluaran.uuid

    whenever(pengeluaranRepository.findById(actualId))
      .thenReturn(actualPengeluaran)

    val expectedVal = pengeluaranRepository.findById(actualId)

    verify(pengeluaranLocalDataSource).findById(actualId)

    assertEquals(actualPengeluaran, expectedVal)
  }

  @Test
  fun `FindDetailById, should success`() = runBlocking {
    pengeluaranRepository = PengeluaranRepositoryImpl(pengeluaranLocalDataSource)

    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]
    val actualKategori = KategoriDummy.getAllKategori()[0]

    val actualDetailPengeluaran = DetailPengeluaran(
      akun = actualAkun,
      pengeluaran = actualPengeluaran,
      kategori = actualKategori
    )

    whenever(pengeluaranRepository.findDetailById(actualPengeluaran.uuid))
      .thenReturn(actualDetailPengeluaran)

    val expectedVal = pengeluaranRepository.findDetailById(actualPengeluaran.uuid)

    verify(pengeluaranLocalDataSource).findDetailById(actualPengeluaran.uuid)

    assertEquals(actualDetailPengeluaran.pengeluaran.uuid, expectedVal.pengeluaran.uuid)
    assertEquals(actualDetailPengeluaran, expectedVal)
  }

  @Test
  fun `GetTotalPengeluaranByDate, should success`() = runBlocking {
    pengeluaranRepository = PengeluaranRepositoryImpl(pengeluaranLocalDataSource)

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    val actualTotalPengeluaran = PengeluaranDummy.getTotalPengeluaran()
    val flow = flow { emit(actualTotalPengeluaran) }

    whenever(pengeluaranRepository.getTotalPengeluaranByDate(fromDate, toDate))
      .thenReturn(flow)

    pengeluaranRepository.getTotalPengeluaranByDate(fromDate, toDate)
      .test {
        val expectedVal = awaitItem()
        assertEquals(actualTotalPengeluaran, expectedVal)
        awaitComplete()
      }
  }

  @Test
  fun `GetTotalPengeluaran with flow, should success`() = runBlocking {
    pengeluaranRepository = PengeluaranRepositoryImpl(pengeluaranLocalDataSource)

    val actualTotalPengeluaran = PengeluaranDummy.getTotalPengeluaran()
    val flow = flow { emit(actualTotalPengeluaran) }

    whenever(pengeluaranRepository.getTotalPengeluaran())
      .thenReturn(flow)

    pengeluaranRepository.getTotalPengeluaran().test {
      val expectedVal = awaitItem()
      assertEquals(expectedVal, actualTotalPengeluaran)
      awaitComplete()
    }
  }

  @Test
  fun `GetMonthlyPengeluaran, should success`() = runBlocking {
    pengeluaranRepository = PengeluaranRepositoryImpl(pengeluaranLocalDataSource)

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]
    val actualKategori = KategoriDummy.getAllKategori()[0]

    val actualDetailPengeluaran1 = DetailPengeluaran(
      akun = actualAkun,
      pengeluaran = actualPengeluaran,
      kategori = actualKategori
    )

    val actualDetailPengeluaran2 = DetailPengeluaran(
      akun = actualAkun,
      pengeluaran = actualPengeluaran,
      kategori = actualKategori
    )

    val actualListDetail = listOf(actualDetailPengeluaran1, actualDetailPengeluaran2)
    val flow = flow { emit(actualListDetail) }

    whenever(pengeluaranRepository.getMonthlyPengeluaran(fromDate, toDate))
      .thenReturn(flow)

    pengeluaranRepository.getMonthlyPengeluaran(fromDate, toDate)
      .test {
        val expectedVal = awaitItem()
        assertEquals(actualListDetail, expectedVal)
        assertEquals(actualListDetail.size, expectedVal.size)
        awaitComplete()
      }
  }

  @Test
  fun `GetPengeluaranByDate, should success`() = runBlocking {
    pengeluaranRepository = PengeluaranRepositoryImpl(pengeluaranLocalDataSource)

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]
    val actualKategori = KategoriDummy.getAllKategori()[0]

    val actualDetailPengeluaran1 = DetailPengeluaran(
      akun = actualAkun,
      pengeluaran = actualPengeluaran,
      kategori = actualKategori
    )

    val actualDetailPengeluaran2 = DetailPengeluaran(
      akun = actualAkun,
      pengeluaran = actualPengeluaran,
      kategori = actualKategori
    )

    val actualListDetail = listOf(actualDetailPengeluaran1, actualDetailPengeluaran2)

    whenever(pengeluaranRepository.getPengeluaranByDate(fromDate, toDate))
      .thenReturn(actualListDetail)

    val expectedVal = pengeluaranRepository.getPengeluaranByDate(fromDate, toDate)

    verify(pengeluaranLocalDataSource).getPengeluaranByDate(fromDate, toDate)

    assertEquals(actualListDetail, expectedVal)
    assertEquals(actualListDetail.size, expectedVal.size)
  }

  @Test
  fun `Save, should success`() = runBlocking {
    pengeluaranRepository = PengeluaranRepositoryImpl(pengeluaranLocalDataSource)

    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]

    whenever(pengeluaranRepository.save(actualPengeluaran))
      .thenReturn(Unit)

    pengeluaranRepository.save(actualPengeluaran)

    verify(pengeluaranLocalDataSource).savePengeluaran(actualPengeluaran)
  }

  @Test
  fun `Delete, should success`() = runBlocking {
    pengeluaranRepository = PengeluaranRepositoryImpl(pengeluaranLocalDataSource)

    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]

    whenever(pengeluaranRepository.delete(actualPengeluaran))
      .thenReturn(Unit)

    pengeluaranRepository.delete(actualPengeluaran)

    verify(pengeluaranLocalDataSource).deletePengeluaran(actualPengeluaran)
  }

  @Test
  fun `Update, should success`() = runBlocking {
    pengeluaranRepository = PengeluaranRepositoryImpl(pengeluaranLocalDataSource)

    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]

    whenever(pengeluaranRepository.update(actualPengeluaran))
      .thenReturn(Unit)

    pengeluaranRepository.update(actualPengeluaran)

    verify(pengeluaranLocalDataSource).updatePengeluaran(actualPengeluaran)
  }
}