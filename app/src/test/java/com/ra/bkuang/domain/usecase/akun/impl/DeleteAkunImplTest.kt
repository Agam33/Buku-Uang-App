package com.ra.bkuang.domain.usecase.akun.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.usecase.akun.DeleteAkun
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.domain.util.ResourceState
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DeleteAkunImplTest {
  private lateinit var deleteAkun: DeleteAkun
  private val repository: AkunRepository = mock()

  @Test
  fun `DeleteAkun, should success`() = runBlocking {
    deleteAkun = DeleteAkunImpl(repository)

    val actualAkun = AkunDummy.getAllAccounts()[0]
    val resourceState = ResourceState.SUCCESS

    whenever(repository.delete(actualAkun))
      .thenReturn(Unit)

    val expectedVal = deleteAkun.invoke(actualAkun.toModel()).last()

    assertEquals(resourceState, expectedVal)
  }
}