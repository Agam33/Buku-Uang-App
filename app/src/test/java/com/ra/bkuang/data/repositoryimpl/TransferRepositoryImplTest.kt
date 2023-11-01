package com.ra.bkuang.data.repositoryimpl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.data.local.TransferLocalDataSource
import com.ra.bkuang.data.entity.DetailTransfer
import com.ra.bkuang.domain.repository.TransferRepository
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.TransferDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class TransferRepositoryImplTest {
  private lateinit var transferRepository: TransferRepository
  private val transferLocalDataSource: TransferLocalDataSource = mock()

  @Test
  fun `FindById, should success`() = runBlocking {
    transferRepository = TransferRepositoryImpl(transferLocalDataSource)

    val actualTransfer = TransferDummy.getAllTransfer()[0]
    val actualId = actualTransfer.uuid

    whenever(transferRepository.findById(actualId))
      .thenReturn(actualTransfer)

    val expectedVal = transferRepository.findById(actualId)

    verify(transferLocalDataSource).findById(actualId)

    assertEquals(expectedVal, actualTransfer)
    assertEquals(expectedVal.uuid, actualTransfer.uuid)
  }

  @Test
  fun `FindDetailById, should success`() = runBlocking {
    transferRepository = TransferRepositoryImpl(transferLocalDataSource)

    val actualTransfer = TransferDummy.getAllTransfer()[0]
    val fromAkun = AkunDummy.getAllAccounts()[0]
    val toAkun = AkunDummy.getAllAccounts()[1]

    val actualDetailTransfer = DetailTransfer(
      fromAkun = fromAkun,
      toAkun = toAkun,
      transfer = actualTransfer
    )

    whenever(transferRepository.findDetailById(actualTransfer.uuid))
      .thenReturn(actualDetailTransfer)

    val expectedVal = transferRepository.findDetailById(actualTransfer.uuid)

    verify(transferLocalDataSource).findDetailById(actualTransfer.uuid)

    assertEquals(actualDetailTransfer, expectedVal)
    assertEquals(actualDetailTransfer.transfer.uuid, expectedVal.transfer.uuid)
  }

  @Test
  fun `GetTransferByDate, should success`() = runBlocking {
    transferRepository = TransferRepositoryImpl(transferLocalDataSource)

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    val actualTransfer = TransferDummy.getAllTransfer()[0]
    val fromAkun = AkunDummy.getAllAccounts()[0]
    val toAkun = AkunDummy.getAllAccounts()[1]

    val actualDetailTransfer1 = DetailTransfer(
      fromAkun = fromAkun,
      toAkun = toAkun,
      transfer = actualTransfer
    )

    val actualDetailTransfer2 = DetailTransfer(
      fromAkun = fromAkun,
      toAkun = toAkun,
      transfer = actualTransfer
    )

    val actualListDetail = listOf(actualDetailTransfer1, actualDetailTransfer2)

    whenever(transferRepository.getTransferByDate(fromDate, toDate))
      .thenReturn(actualListDetail)

    val expectedVal = transferRepository.getTransferByDate(fromDate, toDate)

    verify(transferLocalDataSource).getTransferByDate(fromDate, toDate)

    assertEquals(actualListDetail, expectedVal)
    assertEquals(actualListDetail.size, expectedVal.size)
  }

  @Test
  fun `Save, should success`() = runBlocking {
    transferRepository = TransferRepositoryImpl(transferLocalDataSource)

    val actualTransfer = TransferDummy.getAllTransfer()[0]

    whenever(transferRepository.save(actualTransfer))
      .thenReturn(Unit)

    transferRepository.save(actualTransfer)

    verify(transferLocalDataSource).saveTransfer(actualTransfer)
  }

  @Test
  fun `Delete, should success`() = runBlocking {
    transferRepository = TransferRepositoryImpl(transferLocalDataSource)

    val actualTransfer = TransferDummy.getAllTransfer()[0]

    whenever(transferRepository.delete(actualTransfer))
      .thenReturn(Unit)

    transferRepository.delete(actualTransfer)

    verify(transferLocalDataSource).deleteTransfer(actualTransfer)
  }

  @Test
  fun `Update, should success`() = runBlocking {
    transferRepository = TransferRepositoryImpl(transferLocalDataSource)

    val actualTransfer = TransferDummy.getAllTransfer()[0]

    whenever(transferRepository.update(actualTransfer))
      .thenReturn(Unit)

    transferRepository.update(actualTransfer)

    verify(transferLocalDataSource).updateTransfer(actualTransfer)
  }
}