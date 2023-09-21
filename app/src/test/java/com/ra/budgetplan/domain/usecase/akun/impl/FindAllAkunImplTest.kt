package com.ra.budgetplan.domain.usecase.akun.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.usecase.akun.FindAllAkun
import com.ra.budgetplan.dummy.model.AkunDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class FindAllAkunImplTest {
  private lateinit var findAllAkun: FindAllAkun
  private val repository: AkunRepository = mock()

  @Test
  fun `FindAllAkun, should success`() = runBlocking {
    findAllAkun = FindAllAkunImpl(repository)

    val actualListAkun = AkunDummy.getAllAccounts()

    whenever(repository.findAll())
      .thenReturn(actualListAkun)

    val expectedVal = findAllAkun.invoke()

    assertEquals(actualListAkun.size, expectedVal.size)
    assertEquals(actualListAkun[0].uuid, expectedVal[0].uuid)
  }
}