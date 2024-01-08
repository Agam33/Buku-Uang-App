package com.ra.bkuang.domain.usecase.transaksi.transfer.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.repository.TransferRepository
import com.ra.bkuang.domain.usecase.transaksi.transfer.DeleteTransfer
import com.ra.bkuang.dummy.model.TransferDummy
import com.ra.bkuang.domain.util.ResourceState
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DeleteTransferImplTest {
  private val transferRepository: TransferRepository = mock()
  private lateinit var deleteTransfer: DeleteTransfer

  @Test
  fun `DeleteTransfer, should success`() = runBlocking {
    deleteTransfer = DeleteTransferImpl(transferRepository)

    val actualTransfer = TransferDummy.getAllTransfer()[0]

    whenever(transferRepository.delete(actualTransfer))
      .thenReturn(Unit)

    val expectedVal = deleteTransfer.invoke(actualTransfer.toModel())
    assertEquals(expectedVal, ResourceState.SUCCESS)
  }
}