package com.ra.bkuang.data.local.datasource

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.data.local.database.dao.PembayaranHutangDao
import com.ra.bkuang.data.local.datasource.impl.PembayaranHutangLocalDataSourceImpl
import com.ra.bkuang.data.local.database.entity.DetailPembayaranHutang
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.HutangDummy
import com.ra.bkuang.dummy.model.PembayaranHutangDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

internal class PembayaranHutangLocalDataSourceImplTest {

  private lateinit var pembayaranHutangLocalDataSource: PembayaranHutangLocalDataSource
  private val pembayaranHutangDao: PembayaranHutangDao = mock()

  @Test
  fun `getSizeListPembayaranHutangById, should success`() = runBlocking {
    pembayaranHutangLocalDataSource = PembayaranHutangLocalDataSourceImpl(pembayaranHutangDao)

    val actualSize = 23
    val uuid = UUID.randomUUID()
    whenever(pembayaranHutangLocalDataSource.getSizeListPembayaranHutangById(uuid))
      .thenReturn(flow { emit(actualSize) })

    pembayaranHutangLocalDataSource.getSizeListPembayaranHutangById(uuid)
      .test {
        val item = awaitItem()
        assertEquals(actualSize, item)
        awaitComplete()
      }
  }

  @Test
  fun `FindAllRecordByHutangId, should success`() = runBlocking {
    pembayaranHutangLocalDataSource = PembayaranHutangLocalDataSourceImpl(pembayaranHutangDao)

    val actualHutang = HutangDummy.getAllHutang()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]
    val actualPembayaranHutang = PembayaranHutangDummy.getAllPembayaranHutang()[0]

    val detailHutang1 = DetailPembayaranHutang(
      akun = actualAkun,
      pembayaranHutang = actualPembayaranHutang,
      hutangEntity = actualHutang
    )

    val detailHutang2 = DetailPembayaranHutang(
      akun = actualAkun,
      pembayaranHutang = actualPembayaranHutang,
      hutangEntity = actualHutang
    )

    val actualListDetailHutang = listOf(detailHutang1, detailHutang2)

    whenever(pembayaranHutangLocalDataSource.findAllRecordByHutangId(actualHutang.uuid))
      .thenReturn(actualListDetailHutang)

    val expectedItem = pembayaranHutangLocalDataSource.findAllRecordByHutangId(actualHutang.uuid)

    verify(pembayaranHutangDao).findAllRecordByHutangId(actualHutang.uuid)

    assertEquals(actualListDetailHutang, expectedItem)
    assertEquals(actualListDetailHutang.size, expectedItem.size)
  }

  @Test
  fun `Save, should success`() = runBlocking {
    pembayaranHutangLocalDataSource = PembayaranHutangLocalDataSourceImpl(pembayaranHutangDao)

    val actualPembayaranHutang = PembayaranHutangDummy.getAllPembayaranHutang()[0]

    whenever(pembayaranHutangLocalDataSource.save(actualPembayaranHutang)
    ).thenReturn(Unit)

    pembayaranHutangLocalDataSource.save(actualPembayaranHutang)

    verify(pembayaranHutangDao).save(actualPembayaranHutang)
  }

  @Test
  fun `Delete, should success`() = runBlocking {
    pembayaranHutangLocalDataSource = PembayaranHutangLocalDataSourceImpl(pembayaranHutangDao)

    val actualPembayaranHutang = PembayaranHutangDummy.getAllPembayaranHutang()[0]

    whenever(pembayaranHutangLocalDataSource.delete(actualPembayaranHutang))
      .thenReturn(Unit)

    pembayaranHutangLocalDataSource.delete(actualPembayaranHutang)

    verify(pembayaranHutangDao).delete(actualPembayaranHutang)
  }

  @Test
  fun `Update, should success`() = runBlocking {
    pembayaranHutangLocalDataSource = PembayaranHutangLocalDataSourceImpl(pembayaranHutangDao)

    val actualPembayaranHutang = PembayaranHutangDummy.getAllPembayaranHutang()[0]

    whenever(pembayaranHutangLocalDataSource.update(actualPembayaranHutang))
      .thenReturn(Unit)

    pembayaranHutangLocalDataSource.update(actualPembayaranHutang)

    verify(pembayaranHutangDao).update(actualPembayaranHutang)
  }
}