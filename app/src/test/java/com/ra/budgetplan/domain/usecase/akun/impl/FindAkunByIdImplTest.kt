package com.ra.budgetplan.domain.usecase.akun.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.usecase.akun.FindAkunById
import com.ra.budgetplan.dummy.model.AkunDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class FindAkunByIdImplTest {
  private lateinit var findAkunById: FindAkunById
  private val repository: AkunRepository = mock()

  @Test
  fun `FindAkunById, should success`() = runBlocking {
    findAkunById = FindAkunByIdImpl(repository)

    val actualAkun = AkunDummy.getAllAccounts()[0]
    val actualId = actualAkun.uuid

    whenever(repository.findById(actualId))
      .thenReturn(actualAkun)

    val expectedVal = findAkunById.invoke(actualId)

    assertEquals(actualAkun.uuid, expectedVal.uuid)
    assertEquals(actualAkun.nama, expectedVal.nama)
  }
}