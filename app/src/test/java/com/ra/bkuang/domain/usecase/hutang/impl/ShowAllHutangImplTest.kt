package com.ra.bkuang.domain.usecase.hutang.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.repository.HutangRepository
import com.ra.bkuang.domain.usecase.hutang.ShowAllHutang
import com.ra.bkuang.dummy.model.HutangDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ShowAllHutangImplTest {
  private val hutangRepository: HutangRepository = mock()
  private lateinit var showAllHutang: ShowAllHutang

  @Test
  fun `ShowAllHutang, should success`() = runBlocking {
    showAllHutang = ShowAllHutangImpl(hutangRepository)

    val actualListHutang = HutangDummy.getAllHutang()

    whenever(hutangRepository.findAll())
      .thenReturn(actualListHutang)

    val expectedVal = showAllHutang.invoke()
    assertEquals(actualListHutang.size, expectedVal.data?.size)
  }
}