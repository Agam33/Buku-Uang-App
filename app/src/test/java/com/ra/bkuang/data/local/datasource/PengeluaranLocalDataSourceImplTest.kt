package com.ra.bkuang.data.local.datasource

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.data.local.database.dao.PengeluaranDao
import com.ra.bkuang.data.local.datasource.impl.PengeluaranLocalDataSourceImpl
import com.ra.bkuang.data.local.entity.DetailPengeluaran
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.KategoriDummy
import com.ra.bkuang.dummy.model.PengeluaranDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class PengeluaranLocalDataSourceImplTest {

  private lateinit var pengeluaranLocalDataSource: PengeluaranLocalDataSource
  private val pengeluarDao: PengeluaranDao = mock()

  @Test
  fun `GetTotalPengeluaranByDateAndKategory, should success`() = runBlocking {
    pengeluaranLocalDataSource = PengeluaranLocalDataSourceImpl(pengeluarDao)

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()
    val actualKategori = KategoriDummy.getAllKategori()[0]
    val actualPengeluaran = PengeluaranDummy.getTotalPengeluaran()

    whenever(pengeluaranLocalDataSource.getTotalPengeluaranByDateAndKategory(
      fromDate, toDate, actualKategori.uuid
    )).thenReturn(actualPengeluaran)

    val expectedVal = pengeluaranLocalDataSource.getTotalPengeluaranByDateAndKategory(fromDate, toDate, actualKategori.uuid)

    verify(pengeluarDao).getTotalPengeluaranByDateAndKategori(fromDate, toDate, actualKategori.uuid)

    assertEquals(expectedVal, actualPengeluaran)
  }

  @Test
  fun `GetTotalPengeluaranByDate with null value, should success`() = runBlocking {
    pengeluaranLocalDataSource = PengeluaranLocalDataSourceImpl(pengeluarDao)

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()
    val actualPengeluaran = PengeluaranDummy.getTotalPengeluaran()

    whenever(pengeluaranLocalDataSource.getTotalPengeluaran(fromDate, toDate))
      .thenReturn(actualPengeluaran)

    val expectedVal = pengeluaranLocalDataSource.getTotalPengeluaran(fromDate, toDate)

    verify(pengeluarDao).getTotalPengeluaran(fromDate, toDate)

    assertNotNull(expectedVal)
    assertEquals(expectedVal, actualPengeluaran)
  }

  @Test
  fun `FindById, should success`() = runBlocking {
    pengeluaranLocalDataSource = PengeluaranLocalDataSourceImpl(pengeluarDao)

    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]
    val actualId = actualPengeluaran.idKategori

    whenever(pengeluaranLocalDataSource.findById(actualId))
      .thenReturn(actualPengeluaran)

    val expectedVal = pengeluaranLocalDataSource.findById(actualId)

    verify(pengeluarDao).findById(actualId)

    assertEquals(actualPengeluaran, expectedVal)
  }

  @Test
  fun `FindDetailById, should success`() = runBlocking {
    pengeluaranLocalDataSource = PengeluaranLocalDataSourceImpl(pengeluarDao)

    val actualAkun = AkunDummy.getAllAccounts()[0]
    val actualKategori = KategoriDummy.getAllKategori()[0]
    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]

    val detailPengeluaran = DetailPengeluaran(
      pengeluaran = actualPengeluaran,
      kategori = actualKategori,
      akun = actualAkun
    )

    whenever(pengeluaranLocalDataSource.findDetailById(actualPengeluaran.uuid))
      .thenReturn(detailPengeluaran)

    val expectedVal = pengeluaranLocalDataSource.findDetailById(actualPengeluaran.uuid)

    verify(pengeluarDao).findDetailPengeluaranById(actualPengeluaran.uuid)

    assertEquals(detailPengeluaran, expectedVal)
    assertEquals(detailPengeluaran.pengeluaran.uuid, expectedVal.pengeluaran.uuid)
  }

  @Test
  fun `GetTotalPengeluaranByDate with flow, should success`() = runBlocking {
    pengeluaranLocalDataSource = PengeluaranLocalDataSourceImpl(pengeluarDao)

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()
    val actualPengeluaran = PengeluaranDummy.getTotalPengeluaran()
    val flow = flow { emit(actualPengeluaran) }

    whenever(pengeluaranLocalDataSource.getTotalPengeluaranByDate(fromDate, toDate))
      .thenReturn(flow)

    pengeluaranLocalDataSource.getTotalPengeluaranByDate(fromDate, toDate).test {
      val expectedVal = awaitItem()
      assertEquals(actualPengeluaran, expectedVal)
      awaitComplete()
    }
  }

  @Test
  fun `GetTotalPengeluaran, should success`() = runBlocking {
    pengeluaranLocalDataSource = PengeluaranLocalDataSourceImpl(pengeluarDao)

    val actualPengeluaran = PengeluaranDummy.getTotalPengeluaran()
    val flow = flow { emit(actualPengeluaran) }

    whenever(pengeluaranLocalDataSource.getTotalPengeluaran())
      .thenReturn(flow)

    pengeluaranLocalDataSource.getTotalPengeluaran().test {
      val expectedVal = awaitItem()
      assertEquals(actualPengeluaran, expectedVal)
      awaitComplete()
    }
  }

  @Test
  fun `GetMonthlyPengeluaran, should success`() = runBlocking {
    pengeluaranLocalDataSource = PengeluaranLocalDataSourceImpl(pengeluarDao)

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    val actualAkun = AkunDummy.getAllAccounts()[0]
    val actualKategori = KategoriDummy.getAllKategori()[0]
    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]

    val detailPengeluaran1 = DetailPengeluaran(
      pengeluaran = actualPengeluaran,
      kategori = actualKategori,
      akun = actualAkun
    )

    val detailPengeluaran2 = DetailPengeluaran(
      pengeluaran = actualPengeluaran,
      kategori = actualKategori,
      akun = actualAkun
    )

    val actualListDetailPengeluara = listOf(detailPengeluaran1, detailPengeluaran2)

    val flow = flow { emit(actualListDetailPengeluara) }

    whenever(pengeluaranLocalDataSource.getMonthlyPengeluaran(fromDate, toDate))
      .thenReturn(flow)

    pengeluaranLocalDataSource.getMonthlyPengeluaran(fromDate, toDate).test {
      val expectedVal = awaitItem()
      assertEquals(actualListDetailPengeluara.size, expectedVal.size)
      assertEquals(actualListDetailPengeluara, expectedVal)
      awaitComplete()
    }
  }

  @Test
  fun `GetPengeluaranByDate, should success`() = runBlocking {
    pengeluaranLocalDataSource = PengeluaranLocalDataSourceImpl(pengeluarDao)

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    val actualAkun = AkunDummy.getAllAccounts()[0]
    val actualKategori = KategoriDummy.getAllKategori()[0]
    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]

    val detailPengeluaran1 = DetailPengeluaran(
      pengeluaran = actualPengeluaran,
      kategori = actualKategori,
      akun = actualAkun
    )

    val detailPengeluaran2 = DetailPengeluaran(
      pengeluaran = actualPengeluaran,
      kategori = actualKategori,
      akun = actualAkun
    )

    val actualListDetailPengeluara = listOf(detailPengeluaran1, detailPengeluaran2)

    whenever(pengeluaranLocalDataSource.getPengeluaranByDate(fromDate, toDate))
      .thenReturn(actualListDetailPengeluara)

    val expectedVal = pengeluaranLocalDataSource.getPengeluaranByDate(fromDate, toDate)

    assertEquals(expectedVal, actualListDetailPengeluara)
    assertEquals(expectedVal.size, actualListDetailPengeluara.size)
  }

  @Test
  fun `SavePengeluaran, should success`() = runBlocking {
    pengeluaranLocalDataSource = PengeluaranLocalDataSourceImpl(pengeluarDao)

    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]

    whenever(pengeluaranLocalDataSource.savePengeluaran(actualPengeluaran))
      .thenReturn(Unit)

    pengeluaranLocalDataSource.savePengeluaran(actualPengeluaran)

    verify(pengeluarDao).save(actualPengeluaran)
  }

  @Test
  fun `DeletePengeluaran, should success`() = runBlocking {
    pengeluaranLocalDataSource = PengeluaranLocalDataSourceImpl(pengeluarDao)

    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]

    whenever(pengeluaranLocalDataSource.deletePengeluaran(actualPengeluaran))
      .thenReturn(Unit)

    pengeluaranLocalDataSource.deletePengeluaran(actualPengeluaran)

    verify(pengeluarDao).delete(actualPengeluaran)
  }

  @Test
  fun `IpdatePengeluaran, should success`() = runBlocking {
    pengeluaranLocalDataSource = PengeluaranLocalDataSourceImpl(pengeluarDao)

    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]

    whenever(pengeluaranLocalDataSource.updatePengeluaran(actualPengeluaran))
      .thenReturn(Unit)

    pengeluaranLocalDataSource.updatePengeluaran(actualPengeluaran)

    verify(pengeluarDao).update(actualPengeluaran)
  }
}