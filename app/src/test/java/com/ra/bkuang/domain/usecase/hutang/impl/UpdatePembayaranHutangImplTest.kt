package com.ra.bkuang.domain.usecase.hutang.impl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.repository.HutangRepository
import com.ra.bkuang.domain.repository.PembayaranHutangRepository
import com.ra.bkuang.domain.usecase.hutang.UpdatePembayaranHutang
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.HutangDummy
import com.ra.bkuang.dummy.model.PembayaranHutangDummy
import com.ra.bkuang.domain.util.ResourceState
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class UpdatePembayaranHutangImplTest {
  private val pembayaranHutangRepository: PembayaranHutangRepository = mock()
  private val akunRepository: AkunRepository = mock()
  private val hutangRepository: HutangRepository = mock()
  private lateinit var updatePembayaranHutang: UpdatePembayaranHutang

  @Test
  fun `UpdateHutang, should be success`() = runBlocking {
    updatePembayaranHutang = UpdatePembayaranHutangImpl(akunRepository, hutangRepository, pembayaranHutangRepository)

    val actualPembayaranHutang = PembayaranHutangDummy.getAllPembayaranHutang()[1]
    val actualAkun = AkunDummy.getAllAccounts()[0]
    val actualHutang = HutangDummy.getAllHutang()[0]

    whenever(akunRepository.findById(actualPembayaranHutang.idAkun))
      .thenReturn(actualAkun)
    whenever(hutangRepository.findById(actualPembayaranHutang.idHutang))
      .thenReturn(actualHutang)
    whenever(pembayaranHutangRepository.update(actualPembayaranHutang))
      .thenReturn(Unit)
    whenever(akunRepository.update(actualAkun)).thenReturn(Unit)
    whenever(hutangRepository.update(actualHutang)).thenReturn(Unit)

    updatePembayaranHutang.invoke(actualPembayaranHutang.toModel(), actualPembayaranHutang.toModel()).test {
      val expectedVal1 = awaitItem()
      val expectedVal2 = awaitItem()
      assertEquals(expectedVal1, ResourceState.LOADING)
      assertEquals(expectedVal2, ResourceState.SUCCESS)
      awaitComplete()
    }
  }
}