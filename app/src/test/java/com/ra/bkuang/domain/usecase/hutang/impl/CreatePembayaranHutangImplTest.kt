package com.ra.bkuang.domain.usecase.hutang.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.repository.PembayaranHutangRepository
import com.ra.bkuang.domain.usecase.hutang.CreatePembayaranHutang
import com.ra.bkuang.dummy.model.PembayaranHutangDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CreatePembayaranHutangImplTest {
  private val pembayaranHutangRepository: PembayaranHutangRepository = mock()
  private lateinit var createPembayaranHutang: CreatePembayaranHutang

  @Test
  fun `CreatePembayaranHutang, should success`() = runBlocking {
    createPembayaranHutang = CreatePembayaranHutangImpl(pembayaranHutangRepository)

    val actualPembayaranHutang = PembayaranHutangDummy.getAllPembayaranHutang()[0]

    whenever(pembayaranHutangRepository.save(actualPembayaranHutang))
      .thenReturn(Unit)

    val expectedVal = createPembayaranHutang.invoke(actualPembayaranHutang.toModel())

    verify(pembayaranHutangRepository).save(actualPembayaranHutang)

    assertEquals(expectedVal, Unit)
  }
}