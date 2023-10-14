package com.ra.budgetplan.domain.usecase.hutang.impl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.HutangRepository
import com.ra.budgetplan.domain.repository.PembayaranHutangRepository
import com.ra.budgetplan.domain.usecase.hutang.SavePembayaranHutang
import com.ra.budgetplan.dummy.model.AkunDummy
import com.ra.budgetplan.dummy.model.HutangDummy
import com.ra.budgetplan.dummy.model.PembayaranHutangDummy
import com.ra.budgetplan.util.ResourceState
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SavePembayaranHutangImplTest {
  private val pembayaranHutangRepository: PembayaranHutangRepository = mock()
  private val hutangRepository: HutangRepository = mock()
  private val akunRepository: AkunRepository = mock()
  private lateinit var savePembayaranHutang: SavePembayaranHutang

  @Test
  fun `SavePembayaranHutang, should success`() = runBlocking {
    savePembayaranHutang = SavePembayaranHutangImpl(pembayaranHutangRepository, hutangRepository, akunRepository)

    val actualPembayaranHuntang = PembayaranHutangDummy.getAllPembayaranHutang()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]
    val actualHutang = HutangDummy.getAllHutang()[0]

    whenever(akunRepository.findById(actualPembayaranHuntang.idAkun))
      .thenReturn(actualAkun)
    whenever(hutangRepository.findById(actualPembayaranHuntang.idHutang))
      .thenReturn(actualHutang)

    pembayaranHutangRepository.save(actualPembayaranHuntang)

    whenever(hutangRepository.update(actualHutang))
      .thenReturn(Unit)
    whenever(akunRepository.update(actualAkun))
      .thenReturn(Unit)

    savePembayaranHutang.invoke(actualPembayaranHuntang.toModel()).test {
      val expectedVal1 = awaitItem()
      val expectedVal2 = awaitItem()
      assertEquals(expectedVal1, ResourceState.LOADING)
      assertEquals(expectedVal2, ResourceState.SUCCESS)
      awaitComplete()
    }
  }
}