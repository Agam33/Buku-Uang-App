package com.ra.budgetplan.domain.usecase.hutang.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.entity.DetailPembayaranHutang
import com.ra.budgetplan.domain.repository.PembayaranHutangRepository
import com.ra.budgetplan.domain.usecase.hutang.FindAllRecordPembayaranHutang
import com.ra.budgetplan.dummy.model.AkunDummy
import com.ra.budgetplan.dummy.model.HutangDummy
import com.ra.budgetplan.dummy.model.PembayaranHutangDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class FindAllRecordPembayaranHutangImplTest {
  private val pembayaranHutangRepository: PembayaranHutangRepository = mock()
  private lateinit var findAllRecordPembayaranHutang: FindAllRecordPembayaranHutang

  @Test
  fun `FindAllRecordPembayaranHutang, should be success`() = runBlocking {
    findAllRecordPembayaranHutang = FindAllRecordPembayaranHutangImpl(pembayaranHutangRepository)

    val actualAkun = AkunDummy.getAllAccounts()[0]
    val actualHutang = HutangDummy.getAllHutang()[0]
    val actualPembayaranHutang = PembayaranHutangDummy.getAllPembayaranHutang()[0]
    val actualHutangId = actualHutang.uuid
    val actualDetailPembayaran1 = DetailPembayaranHutang(
      pembayaranHutang = actualPembayaranHutang,
      hutangEntity = actualHutang,
      akun = actualAkun
    )

    val actualDetailPembayaran2 = DetailPembayaranHutang(
      pembayaranHutang = actualPembayaranHutang,
      hutangEntity = actualHutang,
      akun = actualAkun
    )

    val actualListDetail = listOf(actualDetailPembayaran1, actualDetailPembayaran2)

    whenever(pembayaranHutangRepository.findAllRecordByHutangId(actualHutangId))
      .thenReturn(actualListDetail)

    val expectedVal = findAllRecordPembayaranHutang.invoke(actualHutangId)
    assertEquals(expectedVal.data?.size, actualListDetail.size)
  }

  @Test
  fun `FindAllRecordPembayaranHutang, should be empty`() = runBlocking {
    findAllRecordPembayaranHutang = FindAllRecordPembayaranHutangImpl(pembayaranHutangRepository)

    val actualHutang = HutangDummy.getAllHutang()[0]
    val actualHutangId = actualHutang.uuid

    whenever(pembayaranHutangRepository.findAllRecordByHutangId(actualHutangId))
      .thenReturn(ArrayList())

    val expectedVal = findAllRecordPembayaranHutang.invoke(actualHutangId)
    assertEquals(expectedVal.data, null)
  }
}