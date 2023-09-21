package com.ra.budgetplan.domain.usecase.akun.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.usecase.akun.UpdateAkun
import com.ra.budgetplan.dummy.model.AkunDummy
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