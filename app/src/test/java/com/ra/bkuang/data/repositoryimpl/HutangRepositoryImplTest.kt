package com.ra.bkuang.data.repositoryimpl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.data.local.HutangLocalDataSource
import com.ra.bkuang.domain.repository.HutangRepository
import com.ra.bkuang.dummy.model.HutangDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class HutangRepositoryImplTest {
  private lateinit var hutangRepository: HutangRepository
  private val hutangLocalDataSource: HutangLocalDataSource = mock()

  @Test
  fun `Save, should success`() = runBlocking {
    hutangRepository = HutangRepositoryImpl(hutangLocalDataSource)

    val actualHutang = HutangDummy.getAllHutang()[0]

    whenever(hutangRepository.save(actualHutang))
      .thenReturn(Unit)

    hutangRepository.save(actualHutang)

    verify(hutangLocalDataSource).save(actualHutang)
  }

  @Test
  fun `Delete, should success`() = runBlocking {
    hutangRepository = HutangRepositoryImpl(hutangLocalDataSource)

    hutangRepository = HutangRepositoryImpl(hutangLocalDataSource)

    val actualHutang = HutangDummy.getAllHutang()[0]

    whenever(hutangRepository.delete(actualHutang))
      .thenReturn(Unit)

    hutangRepository.delete(actualHutang)

    verify(hutangLocalDataSource).delete(actualHutang)
  }

  @Test
  fun `Update, should success`() = runBlocking {
    hutangRepository = HutangRepositoryImpl(hutangLocalDataSource)

    hutangRepository = HutangRepositoryImpl(hutangLocalDataSource)

    val actualHutang = HutangDummy.getAllHutang()[0]

    whenever(hutangRepository.update(actualHutang))
      .thenReturn(Unit)

    hutangRepository.update(actualHutang)

    verify(hutangLocalDataSource).update(actualHutang)
  }

  @Test
  fun `FindById, should success`() = runBlocking {
    hutangRepository = HutangRepositoryImpl(hutangLocalDataSource)

    val actualHutang = HutangDummy.getAllHutang()[0]
    val actualId = actualHutang.uuid

    whenever(hutangRepository.findById(actualId))
      .thenReturn(actualHutang)

    val expectedVal = hutangRepository.findById(actualId)

    verify(hutangLocalDataSource).findById(actualId)

    assertEquals(actualHutang, expectedVal)
    assertEquals(actualHutang.uuid, expectedVal.uuid)
  }

  @Test
  fun `FindAll, should success`() = runBlocking {
    hutangRepository = HutangRepositoryImpl(hutangLocalDataSource)

    val actualListHutang = HutangDummy.getAllHutang()

    whenever(hutangRepository.findAll())
      .thenReturn(actualListHutang)

    val expectedVal = hutangRepository.findAll()

    verify(hutangLocalDataSource).findAll()

    assertEquals(expectedVal, actualListHutang)
    assertEquals(expectedVal.size, actualListHutang.size)
  }

  @Test
  fun `FindByIdWithFlow, should success`() = runBlocking {
    hutangRepository = HutangRepositoryImpl(hutangLocalDataSource)

    val actualHutang = HutangDummy.getAllHutang()[0]
    val actualId = actualHutang.uuid
    val flow = flow { emit(actualHutang) }

    whenever(hutangRepository.findByIdWithFlow(actualId))
      .thenReturn(flow)

    hutangRepository.findByIdWithFlow(actualId).test {
      val expectedVal = awaitItem()
      assertEquals(expectedVal, actualHutang)
      assertEquals(expectedVal.uuid, actualHutang.uuid)
      awaitComplete()
    }
  }
}