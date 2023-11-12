package com.ra.bkuang.domain.usecase.transaksi.transfer.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.data.local.entity.DetailTransfer
import com.ra.bkuang.domain.repository.TransferRepository
import com.ra.bkuang.domain.usecase.transaksi.transfer.GetTransferByDate
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.TransferDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class GetTransferByDateImplTest {
  private val transferRepository: TransferRepository = mock()
  private lateinit var getTransferByDate: GetTransferByDate

  @Test
  fun `GetTransferByDate, should success`() = runBlocking {
    getTransferByDate = GetTransferByDateImpl(transferRepository)

    val actualTransfer = TransferDummy.getAllTransfer()[0]
    val actualFromAkun = AkunDummy.getAllAccounts()[0]
    val actualToAkun = AkunDummy.getAllAccounts()[1]

    val actualDetail1 = DetailTransfer(
      transfer = actualTransfer,
      fromAkun = actualFromAkun,
      toAkun =  actualToAkun
    )

    val actualDetail2 = DetailTransfer(
      transfer = actualTransfer,
      fromAkun = actualFromAkun,
      toAkun =  actualToAkun
    )

    val actualListDetail = listOf(actualDetail1, actualDetail2)

    val fromDate = LocalDateTime.now()
    val toDate = LocalDateTime.now()

    whenever(transferRepository.getTransferByDate(fromDate, toDate))
      .thenReturn(actualListDetail)

    val expectedVal = getTransferByDate.invoke(fromDate, toDate)
    assertEquals(expectedVal, actualListDetail)
    assertEquals(expectedVal.size, actualListDetail.size)
  }
}