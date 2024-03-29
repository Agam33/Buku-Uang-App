package com.ra.bkuang.domain.usecase.hutang.impl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.repository.HutangRepository
import com.ra.bkuang.domain.repository.PembayaranHutangRepository
import com.ra.bkuang.domain.usecase.hutang.DeleteRecordPembayaranHutang
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.HutangDummy
import com.ra.bkuang.dummy.model.PembayaranHutangDummy
import com.ra.bkuang.util.ResourceState
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DeleteRecordPembayaranHutangImplTest {
  private val pembayaranHutangRepository: PembayaranHutangRepository = mock()
  private val akunRepository: AkunRepository = mock()
  private val hutangRepository: HutangRepository = mock()
  private lateinit var deleteRecordPembayaranHutang: DeleteRecordPembayaranHutang

  @Test
  fun `DeleteRecordPembayaranHutang, should be success`() = runBlocking {
    deleteRecordPembayaranHutang = DeleteRecordPembayaranHutangImpl(hutangRepository, akunRepository, pembayaranHutangRepository)

    val actualAkun = AkunDummy.getAllAccounts()[0]
    val actualHutang = HutangDummy.getAllHutang()[0]
    val actualPembayaranHutang = PembayaranHutangDummy.getAllPembayaranHutang()[0]

    val actualDetailPembayaran = DetailPembayaranHutangModel(
      pembayaranHutangModel = actualPembayaranHutang.toModel(),
      hutangModel = actualHutang.toModel(),
      akunModel = actualAkun.toModel()
    )

    whenever(akunRepository.findById(actualDetailPembayaran.akunModel.uuid))
      .thenReturn(actualAkun)
    whenever(hutangRepository.findById(actualDetailPembayaran.hutangModel.uuid))
      .thenReturn(actualHutang)
    whenever(pembayaranHutangRepository.delete(actualDetailPembayaran.pembayaranHutangModel.toEntity()))
      .thenReturn(Unit)
    whenever(hutangRepository.update(actualHutang)).thenReturn(Unit)
    whenever(akunRepository.update(actualAkun)).thenReturn(Unit)

    deleteRecordPembayaranHutang.invoke(actualDetailPembayaran).test {
      val expectedVal1 = awaitItem()
      val expectedVal2 = awaitItem()
      assertEquals(expectedVal1, ResourceState.LOADING)
      assertEquals(expectedVal2, ResourceState.SUCCESS)
      awaitComplete()
    }
  }
}