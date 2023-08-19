package com.ra.budgetplan.data.local.datasourceimpl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.data.local.AkunLocalDataSource
import com.ra.budgetplan.data.local.database.dao.AkunDao
import com.ra.budgetplan.dummy.model.AkunDummy
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

internal class AkunLocalDataSourceImplTest {

  private lateinit var akunLocalDataSource: AkunLocalDataSource
  private val akunDao: AkunDao = mock()

  @Test
  fun `Get total Money, should success & return total money`() = runBlocking {
    val actualTotalMoney = AkunDummy.getTotalMoney()
    val flow = flow { emit(actualTotalMoney) }

    akunLocalDataSource = AkunLocalDataSourceImpl(akunDao)

    whenever(akunLocalDataSource.getTotalMoney())
      .thenReturn(flow)

    akunLocalDataSource.getTotalMoney().test() {
      val item = expectItem()
      Assertions.assertEquals(actualTotalMoney, item)
      expectComplete()
    }
  }

  @Test
  fun `Save, should success`() = runBlocking {
    val akunEntity = AkunDummy.getAllAccounts()[0]

    akunLocalDataSource = AkunLocalDataSourceImpl(akunDao)

    Mockito.`when`(akunLocalDataSource.save(akunEntity)).thenReturn(Unit)

    akunLocalDataSource.save(akunEntity)

    verify(akunDao).save(akunEntity)
  }

  @Test
  fun `Delete, should success`() = runBlocking {
    val akunEntity = AkunDummy.getAllAccounts()[0]

    akunLocalDataSource = AkunLocalDataSourceImpl(akunDao)

    Mockito.`when`(akunLocalDataSource.delete(akunEntity)).thenReturn(Unit)

    akunLocalDataSource.delete(akunEntity)

    verify(akunDao).delete(akunEntity)
  }

  @Test
  fun `Update, should success`() = runBlocking {
    val akunEntity = AkunDummy.getAllAccounts()[0]

    akunLocalDataSource = AkunLocalDataSourceImpl(akunDao)

    Mockito.`when`(akunLocalDataSource.update(akunEntity)).thenReturn(Unit)

    akunLocalDataSource.update(akunEntity)

    verify(akunDao).update(akunEntity)
  }

  @Test
  fun `FindAll, should success`() = runBlocking {
    val actualListAccount = AkunDummy.getAllAccounts()

    akunLocalDataSource = AkunLocalDataSourceImpl(akunDao)

    Mockito.`when`(akunLocalDataSource.findAll())
      .thenReturn(actualListAccount)

    val expectedList = akunLocalDataSource.findAll()

    verify(akunDao).findAll()

    Assertions.assertEquals(expectedList.size, actualListAccount.size)
    Assertions.assertEquals(expectedList[0].uuid, actualListAccount[0].uuid)
  }

  @Test
  fun `FindById, should success`() = runBlocking {
    val actualAccount = AkunDummy.getAllAccounts()[0]
    val actualAccountId = actualAccount.uuid

    akunLocalDataSource = AkunLocalDataSourceImpl(akunDao)

    Mockito.`when`(akunLocalDataSource.findById(actualAccountId))
      .thenReturn(actualAccount)

    val expectedVal = akunLocalDataSource.findById(actualAccountId)

    verify(akunDao).findById(actualAccountId)

    Assertions.assertEquals(expectedVal, actualAccount)
    Assertions.assertEquals(expectedVal.uuid, actualAccount.uuid)
    Assertions.assertEquals(expectedVal.nama, actualAccount.nama)
  }
}