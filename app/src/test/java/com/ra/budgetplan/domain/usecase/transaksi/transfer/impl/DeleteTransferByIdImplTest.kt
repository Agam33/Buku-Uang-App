package com.ra.budgetplan.domain.usecase.transaksi.transfer.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.TransferRepository
import com.ra.budgetplan.domain.usecase.transaksi.transfer.DeleteTransferById
import com.ra.budgetplan.dummy.model.AkunDummy
import com.ra.budgetplan.dummy.model.TransferDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DeleteTransferByIdImplTest {
  private lateinit var deleteTransferById: DeleteTransferById
  private val transferRepository: TransferRepository = mock()
  private val akunRepository: AkunRepository = mock()

  @Test
  fun `DeleteTransferById, should success`() = runBlocking {
    deleteTransferById = DeleteTransferByIdImpl(transferRepository, akunRepository)

    val actualTransfer = TransferDummy.getAllTransfer()[0]
    val actualTransferID = actualTransfer.uuid
    val actualFromAkun = AkunDummy.getAllAccounts()[0]
    val actualToAkun = AkunDummy.getAllAccounts()[1]

    whenever(transferRepository.findById(actualTransferID)).thenReturn(actualTransfer)
    whenever(transferRepository.delete(actualTransfer)).thenReturn(Unit)
    whenever(akunRepository.findById(actualTransfer.idFromAkun)).thenReturn(actualFromAkun)
    whenever(akunRepository.findById(actualTransfer.idToAkun)).thenReturn(actualToAkun)
    whenever(akunRepository.update(actualFromAkun)).thenReturn(Unit)
    whenever(akunRepository.update(actualToAkun)).thenReturn(Unit)

    val expectedVal = deleteTransferById.invoke(actualTransferID)

    assertEquals(expectedVal, Unit)
  }
}