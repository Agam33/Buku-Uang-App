package com.ra.budgetplan.data.local.datasourceimpl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.data.local.HutangLocalDataSource
import com.ra.budgetplan.data.local.database.dao.HutangDao
import com.ra.budgetplan.dummy.model.HutangDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class HutangLocalDataSourceImplTest {

  private lateinit var hutangLocalDataSource: HutangLocalDataSource
  private val hutangDao: HutangDao = mock()

  @Test
  fun `FindByAlarmId, should success`()  = runBlocking {
    hutangLocalDataSource= HutangLocalDataSourceImpl(hutangDao)

    val hutang = HutangDummy.getAllHutang()[0]
    val actualAlarmId = hutang.idPengingat

    whenever(hutangLocalDataSource.findByAlarmId(actualAlarmId)).thenReturn(hutang)

    val expectedVal = hutangLocalDataSource.findByAlarmId(actualAlarmId)

    verify(hutangDao).findByAlarmId(actualAlarmId)

    assertEquals(expectedVal, hutang)
    assertEquals(expectedVal.idPengingat, actualAlarmId)
  }

  @Test
  fun `Save, should success`() = runBlocking {
    hutangLocalDataSource= HutangLocalDataSourceImpl(hutangDao)

    val hutang = HutangDummy.getAllHutang()[0]

    whenever(hutangLocalDataSource.save(hutang)).thenReturn(Unit)

    hutangLocalDataSource.save(hutang)

    verify(hutangDao).save(hutang)
  }

  @Test
  fun `Delete, should success`() = runBlocking {
    hutangLocalDataSource= HutangLocalDataSourceImpl(hutangDao)

    val hutang = HutangDummy.getAllHutang()[0]

    whenever(hutangLocalDataSource.delete(hutang)).thenReturn(Unit)

    hutangLocalDataSource.delete(hutang)

    verify(hutangDao).delete(hutang)
  }

  @Test
  fun `Update, should success`()  = runBlocking {
    hutangLocalDataSource= HutangLocalDataSourceImpl(hutangDao)

    val hutang = HutangDummy.getAllHutang()[0]

    whenever(hutangLocalDataSource.update(hutang)).thenReturn(Unit)

    hutangLocalDataSource.update(hutang)

    verify(hutangDao).update(hutang)
  }

  @Test
  fun `FindById, should success`()  = runBlocking {
    hutangLocalDataSource= HutangLocalDataSourceImpl(hutangDao)

    val actualHutang = HutangDummy.getAllHutang()[0]
    val actualHutangId = actualHutang.uuid

    whenever(hutangLocalDataSource.findById(actualHutangId)).thenReturn(actualHutang)

    val expectedVal = hutangLocalDataSource.findById(actualHutangId)

    verify(hutangDao).findById(actualHutangId)

    assertEquals(expectedVal, actualHutang)
    assertEquals(expectedVal.uuid, actualHutangId)
  }

  @Test
  fun `FindAll, should success`() = runBlocking {
    hutangLocalDataSource= HutangLocalDataSourceImpl(hutangDao)

    val actualListHutang = HutangDummy.getAllHutang()

    whenever(hutangLocalDataSource.findAll()).thenReturn(actualListHutang)

    val expectedVal = hutangLocalDataSource.findAll()

    verify(hutangDao).findAll()

    assertEquals(expectedVal, actualListHutang)
    assertEquals(expectedVal.size, actualListHutang.size)
  }

  @Test
  fun `FindByIdWithFlow, should success`() = runBlocking {
    hutangLocalDataSource= HutangLocalDataSourceImpl(hutangDao)

    val actualHutang = HutangDummy.getAllHutang()[0]
    val actualId = actualHutang.uuid

    val flow = flow { emit(actualHutang) }

    whenever(hutangLocalDataSource.findByIdWithFlow(actualId)).thenReturn(flow)

    hutangLocalDataSource.findByIdWithFlow(actualId).test {
      val item = expectItem()
      assertEquals(actualHutang, item)
      assertEquals(actualHutang.uuid, item.uuid)
      expectComplete()
    }
  }
}