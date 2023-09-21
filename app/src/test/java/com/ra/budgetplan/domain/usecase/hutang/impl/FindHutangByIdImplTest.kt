package com.ra.budgetplan.domain.usecase.hutang.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.repository.HutangRepository
import com.ra.budgetplan.domain.usecase.hutang.FindHutangById
import com.ra.budgetplan.dummy.model.HutangDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class FindHutangByIdImplTest {
  private val hutangRepository: HutangRepository = mock()
  private lateinit var findHutangById: FindHutangById

  @Test
  fun `FindHutangById, should be success`() = runBlocking {
    findHutangById = FindHutangByIdImpl(hutangRepository)

    val actualHutang = HutangDummy.getAllHutang()[0]

    whenever(hutangRepository.findById(actualHutang.uuid)).thenReturn(actualHutang)

    val expectedVal = findHutangById.invoke(actualHutang.uuid)
    assertEquals(expectedVal, actualHutang.toModel())
    assertEquals(expectedVal.uuid, actualHutang.toModel().uuid)
  }
}