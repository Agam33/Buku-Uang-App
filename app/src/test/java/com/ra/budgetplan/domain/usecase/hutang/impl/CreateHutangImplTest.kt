package com.ra.budgetplan.domain.usecase.hutang.impl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.repository.HutangRepository
import com.ra.budgetplan.domain.usecase.hutang.CreateHutang
import com.ra.budgetplan.dummy.model.HutangDummy
import com.ra.budgetplan.util.ResourceState
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CreateHutangImplTest {
  private val hutangRepository: HutangRepository = mock()
  private lateinit var createHutang: CreateHutang

  @Test
  fun `CreateHutang, should be success`() = runBlocking {
    createHutang = CreateHutangImpl(hutangRepository)

    val actualHutang = HutangDummy.getAllHutang()[0]

    whenever(hutangRepository.save(actualHutang))
      .thenReturn(Unit)

    createHutang.invoke(actualHutang.toModel()).test {
      val expectedVal1 = awaitItem()
      val expectedVal2 = awaitItem()
      assertEquals(expectedVal1, ResourceState.LOADING)
      assertEquals(expectedVal2, ResourceState.SUCCESS)
      awaitComplete()
    }
  }
}