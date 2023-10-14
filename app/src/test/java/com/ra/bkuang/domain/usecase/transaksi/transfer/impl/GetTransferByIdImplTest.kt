package com.ra.bkuang.domain.usecase.transaksi.transfer.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.repository.TransferRepository
import com.ra.bkuang.domain.usecase.transaksi.transfer.GetTransferById
import com.ra.bkuang.dummy.model.TransferDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GetTransferByIdImplTest {
  private val transferRepository: TransferRepository = mock()
  private lateinit var getTransferById: GetTransferById

  @Test
  fun `GetTransferById, should success`() = runBlocking {
    getTransferById = GetTransferByIdImpl(transferRepository)

    val actualTransfer = TransferDummy.getAllTransfer()[0]
    val actualId = actualTransfer.uuid

    whenever(transferRepository.findById(actualId))
      .thenReturn(actualTransfer)

    val expectedVal = getTransferById.invoke(actualId)
    assertEquals(expectedVal, actualTransfer.toModel())
    assertEquals(expectedVal.uuid, actualTransfer.uuid)
  }
}