package com.ra.bkuang.domain.usecase.transaksi.transfer.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.data.entity.TransferEntity
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.repository.TransferRepository
import com.ra.bkuang.domain.usecase.transaksi.transfer.UpdateTransfer
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.TransferDummy
import com.ra.bkuang.util.ResourceState
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class UpdateTransferImplTest {
  private val transferRepository: TransferRepository = mock()
  private val akunRepository: AkunRepository = mock()
  private lateinit var updateTransfer: UpdateTransfer

  @Test
  fun `UpdateTransfer, should success`() = runBlocking {
    updateTransfer = UpdateTransferImpl(transferRepository, akunRepository)

    val actualOldTransfer = TransferDummy.getAllTransfer()[0]
    val actualFromAkun = AkunDummy.getAllAccounts()[0]
    val actualToAkun = AkunDummy.getAllAccounts()[1]
    val actualNewTransfer = TransferEntity(
      uuid = actualOldTransfer.uuid,
      idFromAkun = actualOldTransfer.idFromAkun,
      idToAkun = actualToAkun.uuid,
      deskripsi = "",
      jumlah = actualOldTransfer.jumlah,
      createdAt = actualOldTransfer.createdAt,
      updatedAt = LocalDateTime.now()
    )

    whenever(transferRepository.update(actualNewTransfer)).thenReturn(Unit)
    whenever(akunRepository.findById(actualNewTransfer.idFromAkun)).thenReturn(actualToAkun)
    whenever(akunRepository.findById(actualNewTransfer.idToAkun)).thenReturn(actualFromAkun)
    whenever(akunRepository.update(actualFromAkun)).thenReturn(Unit)
    whenever(akunRepository.update(actualToAkun)).thenReturn(Unit)
    whenever(akunRepository.findById(actualOldTransfer.idFromAkun)).thenReturn(actualFromAkun)
    whenever(akunRepository.findById(actualOldTransfer.idToAkun)).thenReturn(actualToAkun)
    whenever(akunRepository.update(actualFromAkun)).thenReturn(Unit)
    whenever(akunRepository.update(actualToAkun)).thenReturn(Unit)

    val expectedVal = updateTransfer.invoke(actualNewTransfer.toModel(), actualOldTransfer.toModel())
    assertEquals(expectedVal, ResourceState.SUCCESS)
  }
}