package com.ra.bkuang.data.repositoryimpl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.data.local.PembayaranHutangLocalDataSource
import com.ra.bkuang.data.local.entity.DetailPembayaranHutang
import com.ra.bkuang.domain.repository.PembayaranHutangRepository
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.HutangDummy
import com.ra.bkuang.dummy.model.PembayaranHutangDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.UUID

internal class PembayaranHutangRepositoryImplTest {
  private lateinit var pembayaranHutangRepository : PembayaranHutangRepository
  private val pembayaranHutangLocalDataSource: PembayaranHutangLocalDataSource = mock()


  @Test
  fun `getSizeListPembayaranHutangById(), should success`() = runBlocking {
    pembayaranHutangRepository = PembayaranHutangRepositoryImpl(pembayaranHutangLocalDataSource)

    val actualSize = 23
    val uuid = UUID.randomUUID()

    whenever(pembayaranHutangRepository.getSizeListPembayaranHutangById(uuid))
      .thenReturn( flow { emit (actualSize) })

    pembayaranHutangRepository.getSizeListPembayaranHutangById(uuid)
      .test {
        val item = awaitItem()
        assertEquals(item, actualSize)
        awaitComplete()
      }
  }

  @Test
  fun `FindAllRecordByHutangId, should success`() = runBlocking {
    pembayaranHutangRepository = PembayaranHutangRepositoryImpl(pembayaranHutangLocalDataSource)

    val actualPembarayanHutang = PembayaranHutangDummy.getAllPembayaranHutang()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]
    val actualHutang = HutangDummy.getAllHutang()[0]

    val detailPembayaranHutang1 = DetailPembayaranHutang(
      pembayaranHutang =  actualPembarayanHutang,
      akun = actualAkun,
      hutangEntity = actualHutang
    )

    val detailPembayaranHutang2 = DetailPembayaranHutang(
      pembayaranHutang =  actualPembarayanHutang,
      akun = actualAkun,
      hutangEntity = actualHutang
    )

    val actualList = listOf(detailPembayaranHutang1, detailPembayaranHutang2)

    whenever(pembayaranHutangRepository.findAllRecordByHutangId(actualHutang.uuid))
      .thenReturn(actualList)

    val expectedVal = pembayaranHutangRepository.findAllRecordByHutangId(actualHutang.uuid)

    verify(pembayaranHutangLocalDataSource).findAllRecordByHutangId(actualHutang.uuid)

    assertEquals(actualList, expectedVal)
    assertEquals(actualList.size, expectedVal.size)

  }

  @Test
  fun `Save, should success`() = runBlocking {
    pembayaranHutangRepository = PembayaranHutangRepositoryImpl(pembayaranHutangLocalDataSource)

    val actualPembayaranHutang = PembayaranHutangDummy.getAllPembayaranHutang()[0]

    whenever(pembayaranHutangRepository.save(actualPembayaranHutang))
      .thenReturn(Unit)

    pembayaranHutangRepository.save(actualPembayaranHutang)

    verify(pembayaranHutangLocalDataSource).save(actualPembayaranHutang)
  }

  @Test
  fun `Delete, should success`() = runBlocking {
    pembayaranHutangRepository = PembayaranHutangRepositoryImpl(pembayaranHutangLocalDataSource)

    val actualPembayaranHutang = PembayaranHutangDummy.getAllPembayaranHutang()[0]

    whenever(pembayaranHutangRepository.delete(actualPembayaranHutang))
      .thenReturn(Unit)

    pembayaranHutangRepository.delete(actualPembayaranHutang)

    verify(pembayaranHutangLocalDataSource).delete(actualPembayaranHutang)
  }

  @Test
  fun `Update, should success`() = runBlocking {
    pembayaranHutangRepository = PembayaranHutangRepositoryImpl(pembayaranHutangLocalDataSource)

    val actualPembayaranHutang = PembayaranHutangDummy.getAllPembayaranHutang()[0]

    whenever(pembayaranHutangRepository.update(actualPembayaranHutang))
      .thenReturn(Unit)

    pembayaranHutangRepository.update(actualPembayaranHutang)

    verify(pembayaranHutangLocalDataSource).update(actualPembayaranHutang)
  }
}