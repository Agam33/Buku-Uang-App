package com.ra.budgetplan.data.repositoryimpl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.data.local.AkunLocalDataSource
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.dummy.model.AkunDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AkunRepositoryImplTest {
  private lateinit var akunRepository: AkunRepository
  private val akunLocalDataSource: AkunLocalDataSource = mock()

  @Test
  fun `GetTotalMoney, should success`() = runBlocking {
    akunRepository = AkunRepositoryImpl(akunLocalDataSource)

    val actualTotalMoney = AkunDummy.getTotalMoney()
    val flow = flow { emit(actualTotalMoney) }

    whenever(akunRepository.getTotalMoney()).thenReturn(flow)

    akunRepository.getTotalMoney().test {
      val expectedVal = awaitItem()
      assertEquals(expectedVal, actualTotalMoney)
      awaitComplete()
    }
  }

  @Test
  fun `Save, should success`() = runBlocking {
    akunRepository = AkunRepositoryImpl(akunLocalDataSource)

    val actualAkun = AkunDummy.getAllAccounts()[0]

    whenever(akunRepository.save(actualAkun)).thenReturn(Unit)

    akunRepository.save(actualAkun)

    verify(akunLocalDataSource).save(actualAkun)
  }

  @Test
  fun `Delete, should success`() = runBlocking {
    akunRepository = AkunRepositoryImpl(akunLocalDataSource)

    val actualAkun = AkunDummy.getAllAccounts()[0]

    whenever(akunRepository.delete(actualAkun)).thenReturn(Unit)

    akunRepository.delete(actualAkun)

    verify(akunLocalDataSource).delete(actualAkun)
  }

  @Test
  fun `FindAll, should success`() = runBlocking {
    akunRepository = AkunRepositoryImpl(akunLocalDataSource)

    val actualListAkun = AkunDummy.getAllAccounts()

    whenever(akunRepository.findAll()).thenReturn(actualListAkun)

    val expectedVal = akunRepository.findAll()

    verify(akunLocalDataSource).findAll()

    assertEquals(actualListAkun, expectedVal)
    assertEquals(actualListAkun.size, expectedVal.size)
  }

  @Test

  fun `FindById, should success`() = runBlocking {
    akunRepository = AkunRepositoryImpl(akunLocalDataSource)

    val actualAkun = AkunDummy.getAllAccounts()[0]
    val actualId = actualAkun.uuid

    whenever(akunRepository.findById(actualId))
      .thenReturn(actualAkun)

    val expectedVal = akunRepository.findById(actualId)

    verify(akunLocalDataSource).findById(actualId)

    assertEquals(actualAkun, expectedVal)
    assertEquals(actualAkun.uuid, expectedVal.uuid)
  }
}