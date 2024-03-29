package com.ra.bkuang.data.local.datasource

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.data.local.database.dao.PendapatanDao
import com.ra.bkuang.data.local.datasource.impl.PendapatanLocalDataSourceImpl
import com.ra.bkuang.data.local.database.entity.DetailPendapatan
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.KategoriDummy
import com.ra.bkuang.dummy.model.PendapatanDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class PendapatanLocalDataSourceImplTest {

  private lateinit var pendapateLocalDataSource: PendapatanLocalDataSource
  private val pendapatanDao: PendapatanDao = mock()

  @Test
  fun `GetTotalPendapatanByDate with null value, should success`() = runBlocking {
    pendapateLocalDataSource = PendapatanLocalDataSourceImpl(pendapatanDao)

    val actualTotalPendapatan = PendapatanDummy.getTotalPendapatan()
    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    whenever(pendapatanDao.getTotalPendapatan(fromDate, toDate)).thenReturn(actualTotalPendapatan)

    val expectedVal = pendapatanDao.getTotalPendapatan(fromDate, toDate)

    verify(pendapatanDao).getTotalPendapatan(fromDate, toDate)

    Assertions.assertEquals(actualTotalPendapatan, expectedVal)
  }

  @Test
  fun `FindById, should success`() = runBlocking {
    pendapateLocalDataSource = PendapatanLocalDataSourceImpl(pendapatanDao)

    val actualPendapatan = PendapatanDummy.getAllPendapatan()[0]
    val actualId = actualPendapatan.uuid

    whenever(pendapateLocalDataSource.findById(actualId))
      .thenReturn(actualPendapatan)

    val expectedVal = pendapateLocalDataSource.findById(actualId)

    verify(pendapatanDao).findById(actualId)

    Assertions.assertEquals(actualPendapatan, expectedVal)
    Assertions.assertEquals(actualPendapatan.uuid, expectedVal.uuid)
  }

  @Test
  fun `FindDetailById, should success`() = runBlocking {
    pendapateLocalDataSource = PendapatanLocalDataSourceImpl(pendapatanDao)

    val actualPendapatan = PendapatanDummy.getAllPendapatan()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]
    val actualKategori = KategoriDummy.getAllKategori()[0]

    val actualDetailPendapatan = DetailPendapatan(
      pendapatan = actualPendapatan,
      akun = actualAkun,
      kategori = actualKategori
    )

    whenever(pendapateLocalDataSource.findDetailById(actualPendapatan.uuid))
      .thenReturn(actualDetailPendapatan)

    val expectedVal = pendapateLocalDataSource.findDetailById(actualPendapatan.uuid)

    verify(pendapatanDao).findDetailPendapatanById(actualPendapatan.uuid)

    Assertions.assertEquals(actualDetailPendapatan, expectedVal)
    Assertions.assertEquals(actualDetailPendapatan.pendapatan.uuid, expectedVal.pendapatan.uuid)
  }

  @Test
  fun `GetTotalPendapatanByDate, should success`() = runBlocking {
    pendapateLocalDataSource = PendapatanLocalDataSourceImpl(pendapatanDao)

    val actualTotalPendapatan = PendapatanDummy.getTotalPendapatan()
    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    val flow = flow { emit(actualTotalPendapatan) }

    whenever(pendapatanDao.getTotalPendapatanByDate(fromDate, toDate))
      .thenReturn(flow)

    pendapatanDao.getTotalPendapatanByDate(fromDate, toDate).test {
      val expectedVal = awaitItem()
      Assertions.assertEquals(actualTotalPendapatan, expectedVal)
      awaitComplete()
    }

  }

  @Test
  fun `GetTotalPendapatan, should success`() = runBlocking {
    pendapateLocalDataSource = PendapatanLocalDataSourceImpl(pendapatanDao)

    val actualTotalPendapatan = PendapatanDummy.getTotalPendapatan()

    val flow = flow { emit(actualTotalPendapatan) }

    whenever(pendapatanDao.getTotalPendapatan())
      .thenReturn(flow)

    pendapatanDao.getTotalPendapatan().test {
      val expectedVal = awaitItem()
      Assertions.assertEquals(actualTotalPendapatan, expectedVal)
      awaitComplete()
    }
  }

  @Test
  fun `GetPendapatanByDate, should success`() = runBlocking {
    pendapateLocalDataSource = PendapatanLocalDataSourceImpl(pendapatanDao)

    val actualListPendapatan = PendapatanDummy.getAllPendapatan()
    val actualAkun = AkunDummy.getAllAccounts()[0]
    val actualKategori = KategoriDummy.getAllKategori()[0]
    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    val detail1 = DetailPendapatan(
      akun = actualAkun,
      kategori = actualKategori,
      pendapatan = actualListPendapatan[0]
    )

    val detail2 = DetailPendapatan(
      akun = actualAkun,
      kategori = actualKategori,
      pendapatan = actualListPendapatan[1]
    )

    val detail3 = DetailPendapatan(
      akun = actualAkun,
      kategori = actualKategori,
      pendapatan = actualListPendapatan[2]
    )

    val listDetailPendapatan = listOf(detail1, detail2, detail3)

    whenever(pendapateLocalDataSource.getPendapatanByDate(fromDate, toDate))
      .thenReturn(listDetailPendapatan)

    val expectedVal = pendapateLocalDataSource.getPendapatanByDate(fromDate, toDate)

    verify(pendapatanDao).getPendapatanByDate(fromDate, toDate)

    Assertions.assertEquals(expectedVal, listDetailPendapatan)
    Assertions.assertEquals(expectedVal.size, listDetailPendapatan.size)
  }

  @Test
  fun `SavePendapatan, should success`() = runBlocking {
    pendapateLocalDataSource = PendapatanLocalDataSourceImpl(pendapatanDao)

    val actualPendapatan = PendapatanDummy.getAllPendapatan()[0]

    whenever(pendapateLocalDataSource.savePendapatan(actualPendapatan))
      .thenReturn(Unit)

    pendapateLocalDataSource.savePendapatan(actualPendapatan)

    verify(pendapatanDao).save(actualPendapatan)
  }

  @Test
  fun `DeletePendapatan, should success`() = runBlocking {
    pendapateLocalDataSource = PendapatanLocalDataSourceImpl(pendapatanDao)

    val actualPendapatan = PendapatanDummy.getAllPendapatan()[0]

    whenever(pendapateLocalDataSource.deletePendapatan(actualPendapatan))
      .thenReturn(Unit)

    pendapateLocalDataSource.deletePendapatan(actualPendapatan)

    verify(pendapatanDao).delete(actualPendapatan)
  }

  @Test
  fun `UpdatePendapatan, should success`() = runBlocking {
    pendapateLocalDataSource = PendapatanLocalDataSourceImpl(pendapatanDao)

    val actualPendapatan = PendapatanDummy.getAllPendapatan()[0]

    whenever(pendapateLocalDataSource.deletePendapatan(actualPendapatan))
      .thenReturn(Unit)

    pendapateLocalDataSource.deletePendapatan(actualPendapatan)

    verify(pendapatanDao).delete(actualPendapatan)
  }
}