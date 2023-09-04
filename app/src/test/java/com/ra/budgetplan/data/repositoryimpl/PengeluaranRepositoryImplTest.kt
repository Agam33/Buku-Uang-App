package com.ra.budgetplan.data.repositoryimpl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.data.local.PendapatanLocalDataSource
import com.ra.budgetplan.domain.entity.DetailPendapatan
import com.ra.budgetplan.domain.repository.PendapatanRepository
import com.ra.budgetplan.dummy.model.AkunDummy
import com.ra.budgetplan.dummy.model.KategoriDummy
import com.ra.budgetplan.dummy.model.PendapatanDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class PengeluaranRepositoryImplTest {
  private lateinit var pendapatanRepository: PendapatanRepository
  private val pendapatanLocalDataSource: PendapatanLocalDataSource = mock()

  @Test
  fun `GetTotalPendapatan, should success`() = runBlocking {
    pendapatanRepository = PendapatanRepositoryImpl(pendapatanLocalDataSource)

    val actualTotalPendapatan = PendapatanDummy.getTotalPendapatan()

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    whenever(pendapatanRepository.getTotalPendapatan(fromDate, toDate))
      .thenReturn(actualTotalPendapatan)

    val expectedVal = pendapatanRepository.getTotalPendapatan(fromDate, toDate)

    verify(pendapatanLocalDataSource).getTotalPendapatan(fromDate, toDate)

    assertEquals(actualTotalPendapatan, expectedVal)
  }

  @Test
  fun `FindById, should success`() = runBlocking {
    pendapatanRepository = PendapatanRepositoryImpl(pendapatanLocalDataSource)

    val actualPendapatan = PendapatanDummy.getAllPendapatan()[0]
    val actualId = actualPendapatan.uuid

    whenever(pendapatanRepository.findById(actualId))
      .thenReturn(actualPendapatan)

    val expectedVal = pendapatanRepository.findById(actualId)

    verify(pendapatanLocalDataSource).findById(actualId)

    assertEquals(expectedVal, actualPendapatan)
    assertEquals(expectedVal.uuid, actualPendapatan.uuid)
  }

  @Test
  fun `FindDetailById, should success`() = runBlocking {
    pendapatanRepository = PendapatanRepositoryImpl(pendapatanLocalDataSource)

    val actualPendapatan = PendapatanDummy.getAllPendapatan()[0]
    val actualKategori = KategoriDummy.getAllKategori()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]

    val actualDetailPendapatan = DetailPendapatan(
      kategori = actualKategori,
      akun = actualAkun,
      pendapatan = actualPendapatan
    )

    whenever(pendapatanRepository.findDetailById(actualPendapatan.uuid))
      .thenReturn(actualDetailPendapatan)

    val expectedVal = pendapatanRepository.findDetailById(actualPendapatan.uuid)

    verify(pendapatanLocalDataSource).findDetailById(actualPendapatan.uuid)

    assertEquals(actualDetailPendapatan, expectedVal)
    assertEquals(actualDetailPendapatan.pendapatan.uuid, expectedVal.pendapatan.uuid)
  }

  @Test
  fun `GetTotalPendapatanByDate, should success`() = runBlocking {
    pendapatanRepository = PendapatanRepositoryImpl(pendapatanLocalDataSource)

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    val actualTotal = PendapatanDummy.getTotalPendapatan()
    val flow = flow { emit(actualTotal) }

    whenever(pendapatanRepository.getTotalPendapatanByDate(fromDate, toDate))
      .thenReturn(flow)

    pendapatanRepository.getTotalPendapatanByDate(fromDate, toDate)
      .test {
        val expectedVal = awaitItem()
        assertEquals(actualTotal, expectedVal)
        awaitComplete()
      }
  }

  @Test
  fun `GetTotalPendapatan with flow, should success`() = runBlocking {
    pendapatanRepository = PendapatanRepositoryImpl(pendapatanLocalDataSource)

    val actualTotal = PendapatanDummy.getTotalPendapatan()
    val flow = flow { emit(actualTotal) }

    whenever(pendapatanRepository.getTotalPendapatan())
      .thenReturn(flow)

    pendapatanRepository.getTotalPendapatan()
      .test {
        val expectedVal = awaitItem()
        assertEquals(actualTotal, expectedVal)
        awaitComplete()
      }
  }

  @Test
  fun `GetPendapatanByDate, should success`() = runBlocking {
    pendapatanRepository = PendapatanRepositoryImpl(pendapatanLocalDataSource)

    val actualPendapatan = PendapatanDummy.getAllPendapatan()[0]
    val actualKategori = KategoriDummy.getAllKategori()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]

    val actualDetailPendapatan1 = DetailPendapatan(
      kategori = actualKategori,
      akun = actualAkun,
      pendapatan = actualPendapatan
    )

    val actualDetailPendapatan2 = DetailPendapatan(
      kategori = actualKategori,
      akun = actualAkun,
      pendapatan = actualPendapatan
    )

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    val actualList = listOf(actualDetailPendapatan1, actualDetailPendapatan2)

    whenever(pendapatanRepository.getPendapatanByDate(fromDate, toDate))
      .thenReturn(actualList)

    val expectedVal = pendapatanRepository.getPendapatanByDate(fromDate, toDate)

    verify(pendapatanLocalDataSource).getPendapatanByDate(fromDate, toDate)

    assertEquals(expectedVal, actualList)
    assertEquals(expectedVal.size, actualList.size)
  }

  @Test
  fun `Save, should success`() = runBlocking {
    pendapatanRepository = PendapatanRepositoryImpl(pendapatanLocalDataSource)

    val actualPendapatan = PendapatanDummy.getAllPendapatan()[0]

    whenever(pendapatanRepository.save(actualPendapatan))
      .thenReturn(Unit)

    pendapatanRepository.save(actualPendapatan)

    verify(pendapatanLocalDataSource).savePendapatan(actualPendapatan)
  }

  @Test
  fun `Delete, should success`() = runBlocking {
    pendapatanRepository = PendapatanRepositoryImpl(pendapatanLocalDataSource)

    val actualPendapatan = PendapatanDummy.getAllPendapatan()[0]

    whenever(pendapatanRepository.update(actualPendapatan))
      .thenReturn(Unit)

    pendapatanRepository.update(actualPendapatan)

    verify(pendapatanLocalDataSource).updatePendapatan(actualPendapatan)
  }

  @Test
  fun `Update, should success`() = runBlocking {
    pendapatanRepository = PendapatanRepositoryImpl(pendapatanLocalDataSource)

    val actualPendapatan = PendapatanDummy.getAllPendapatan()[0]

    whenever(pendapatanRepository.update(actualPendapatan))
      .thenReturn(Unit)

    pendapatanRepository.update(actualPendapatan)

    verify(pendapatanLocalDataSource).updatePendapatan(actualPendapatan)
  }
}