package com.ra.budgetplan.domain.usecase.transaksi.transfer.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.repository.TransferRepository
import com.ra.budgetplan.domain.usecase.transaksi.transfer.DeleteTransfer
import com.ra.budgetplan.dummy.model.TransferDummy
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
    assertEquals(expectedVal, Unit)
  }
}