package com.ra.budgetplan.domain.usecase.transaksi.transfer.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.TransferRepository
import com.ra.budgetplan.domain.usecase.transaksi.transfer.SaveTransfer
import com.ra.budgetplan.dummy.model.AkunDummy
import com.ra.budgetplan.dummy.model.TransferDummy
import com.ra.budgetplan.util.ResourceState
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SaveTransferImplTest {
  private val transferRepository: TransferRepository = mock()
  private val akunRepository: AkunRepository = mock()
  private lateinit var saveTransfer: SaveTransfer

  @Test
  fun `SaveTransfer, should success`() = runBlocking {
    saveTransfer = SaveTransferImpl(transferRepository, akunRepository)

    val actualTransfer = TransferDummy.getAllTransfer()[0]
    val actualFromAkun = AkunDummy.getAllAccounts()[0]
    val actualToAkun = AkunDummy.getAllAccounts()[1]

    whenever(akunRepository.findById(actualTransfer.idFromAkun)).thenReturn(actualFromAkun)
    whenever(akunRepository.findById(actualTransfer.idToAkun)).thenReturn(actualToAkun)
    whenever(transferRepository.save(actualTransfer)).thenReturn(Unit)

    val expectedVal = saveTransfer.invoke(actualTransfer.toModel())

    verify(akunRepository).findById(actualTransfer.idFromAkun)
    verify(akunRepository).findById(actualTransfer.idToAkun)
    verify(transferRepository).save(actualTransfer)

    assertEquals(expectedVal, ResourceState.SUCCESS)
  }
}