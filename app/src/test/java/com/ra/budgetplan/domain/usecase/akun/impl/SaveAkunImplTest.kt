package com.ra.budgetplan.domain.usecase.akun.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.usecase.akun.SaveAkun
import com.ra.budgetplan.dummy.model.AkunDummy
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SaveAkunImplTest {
  private lateinit var saveAkunImpl: SaveAkun
  private val repository: AkunRepository = mock()

  @Test
  fun `SaveAkun, should success`() = runBlocking {
    saveAkunImpl = SaveAkunImpl(repository)

    val actualAkun = AkunDummy.getAllAccounts()[0]

    whenever(repository.save(actualAkun))
      .thenReturn(Unit)

    saveAkunImpl.invoke(actualAkun.toModel())

    verify(repository).save(actualAkun)
  }
}