package com.ra.bkuang.data.local.datasource

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.data.local.database.dao.TransferDao
import com.ra.bkuang.data.local.datasource.impl.TransferLocalDataSourceImpl
import com.ra.bkuang.data.local.database.entity.DetailTransfer
import com.ra.bkuang.data.local.database.entity.TransferEntity
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.TransferDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.UUID

internal class TransferLocalDataSourceImplTest {

  private lateinit var transferLocalDataSource: TransferLocalDataSource
  private val transferDao: TransferDao = mock()


  @Test
  fun `FindById, should success`() = runBlocking {
    transferLocalDataSource = TransferLocalDataSourceImpl(transferDao)

    val actualTransfer = TransferDummy.getAllTransfer()[0]
    val actualId = actualTransfer.uuid

    whenever(transferLocalDataSource.findById(actualId)).thenReturn(actualTransfer)

    val expectedVal = transferLocalDataSource.findById(actualId)

    verify(transferDao).findById(actualId)

    assertEquals(expectedVal, actualTransfer)
    assertEquals(expectedVal.uuid, actualTransfer.uuid)
  }

  @Test
  fun `FindDetailById, should success`() = runBlocking {
    transferLocalDataSource = TransferLocalDataSourceImpl(transferDao)

    val fromAkun = AkunDummy.getAllAccounts()[0]
    val toAkun = AkunDummy.getAllAccounts()[1]

    val transfer = TransferEntity(
      UUID.randomUUID(),
      fromAkun.uuid,
      toAkun.uuid,
      "",
      0,
      LocalDateTime.now(),
      LocalDateTime.now(),
    )

    val detailTransfer = DetailTransfer(
      transfer = transfer,
      fromAkun = fromAkun,
      toAkun = toAkun
    )

    whenever(transferLocalDataSource.findDetailById(transfer.uuid))
      .thenReturn(detailTransfer)

    val expectedVal = transferLocalDataSource.findDetailById(transfer.uuid)

    verify(transferDao).findDetailTransferById(transfer.uuid)

    assertEquals(detailTransfer, expectedVal)
    assertEquals(detailTransfer.transfer.uuid, expectedVal.transfer.uuid)
  }

  @Test
  fun `GetTransferByDate, should success`() = runBlocking {
    transferLocalDataSource = TransferLocalDataSourceImpl(transferDao)

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    val fromAkun = AkunDummy.getAllAccounts()[0]
    val toAkun = AkunDummy.getAllAccounts()[1]

    val transfer = TransferEntity(
      UUID.randomUUID(),
      fromAkun.uuid,
      toAkun.uuid,
      "",
      0,
      LocalDateTime.now(),
      LocalDateTime.now(),
    )

    val detailTransfer1 = DetailTransfer(
      transfer = transfer,
      fromAkun = fromAkun,
      toAkun = toAkun
    )

    val detailTransfer2 = DetailTransfer(
      transfer = transfer,
      fromAkun = fromAkun,
      toAkun = toAkun
    )

    val detailTransfer3 = DetailTransfer(
      transfer = transfer,
      fromAkun = fromAkun,
      toAkun = toAkun
    )

    val actualListDetailTransfer = listOf(detailTransfer1, detailTransfer2, detailTransfer3)

    whenever(transferLocalDataSource.getTransferByDate(fromDate, toDate))
      .thenReturn(actualListDetailTransfer)

    val expectedVal = transferLocalDataSource.getTransferByDate(fromDate, toDate)

    verify(transferDao).getTransferByDate(fromDate, toDate)

    assertEquals(actualListDetailTransfer, expectedVal)
    assertEquals(actualListDetailTransfer.size, expectedVal.size)
  }

  @Test
  fun `SaveTransfer, should success`() = runBlocking {
    transferLocalDataSource = TransferLocalDataSourceImpl(transferDao)

    val actualTransfer = TransferDummy.getAllTransfer()[0]

    whenever(transferLocalDataSource.saveTransfer(actualTransfer)).thenReturn(Unit)

    transferLocalDataSource.saveTransfer(actualTransfer)

    verify(transferDao).save(actualTransfer)
  }

  @Test
  fun `DeleteTransfer, should success`() = runBlocking  {
    transferLocalDataSource = TransferLocalDataSourceImpl(transferDao)

    val actualTransfer = TransferDummy.getAllTransfer()[0]

    whenever(transferLocalDataSource.deleteTransfer(actualTransfer)).thenReturn(Unit)

    transferLocalDataSource.deleteTransfer(actualTransfer)

    verify(transferDao).delete(actualTransfer)
  }

  @Test
  fun `UpdateTransfer, should success`() = runBlocking {
    transferLocalDataSource = TransferLocalDataSourceImpl(transferDao)

    val actualTransfer = TransferDummy.getAllTransfer()[0]

    whenever(transferLocalDataSource.updateTransfer(actualTransfer)).thenReturn(Unit)

    transferLocalDataSource.updateTransfer(actualTransfer)

    verify(transferDao).update(actualTransfer)
  }
}