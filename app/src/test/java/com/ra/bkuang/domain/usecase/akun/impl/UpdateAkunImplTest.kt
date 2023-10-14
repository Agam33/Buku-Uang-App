package com.ra.bkuang.domain.usecase.akun.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.usecase.akun.UpdateAkun
import com.ra.bkuang.dummy.model.AkunDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class UpdateAkunImplTest {
  private lateinit var updateAkun: UpdateAkun
  private val repository: AkunRepository = mock()

  @Test
  fun `UpdateAkun, should success`() = runBlocking {
    updateAkun = UpdateAkunImpl(repository)

    val actualAkun = AkunDummy.getAllAccounts()[0]

    whenever(repository.update(actualAkun))
      .thenReturn(Unit)

    updateAkun.invoke(actualAkun.toModel())

    verify(repository).update(actualAkun)
  }
}