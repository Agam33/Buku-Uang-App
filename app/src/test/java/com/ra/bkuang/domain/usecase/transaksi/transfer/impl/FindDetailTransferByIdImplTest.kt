package com.ra.bkuang.domain.usecase.transaksi.transfer.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.data.entity.DetailTransfer
import com.ra.bkuang.domain.repository.TransferRepository
import com.ra.bkuang.domain.usecase.transaksi.transfer.FindDetailTransferById
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.TransferDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class FindDetailTransferByIdImplTest {
  private val transferRepository: TransferRepository = mock()
  private lateinit var findDetailTransferById: FindDetailTransferById

  @Test
  fun `FindDetailTransferById, should success`() = runBlocking {
    findDetailTransferById = FindDetailTransferByIdImpl(transferRepository)

    val actualTransfer = TransferDummy.getAllTransfer()[0]
    val actualId = actualTransfer.uuid
    val actualFromAkun = AkunDummy.getAllAccounts()[0]
    val actualToAkun = AkunDummy.getAllAccounts()[1]

    val actualDetail = DetailTransfer(
      transfer = actualTransfer,
      fromAkun = actualFromAkun,
      toAkun =  actualToAkun
    )

    whenever(transferRepository.findDetailById(actualId))
      .thenReturn(actualDetail)

    val expectedVal = findDetailTransferById.invoke(actualId)
    assertEquals(actualDetail, expectedVal)
    assertEquals(actualDetail.transfer.uuid, expectedVal.transfer.uuid)
  }
}