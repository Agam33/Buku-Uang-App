package com.ra.bkuang.domain.usecase.akun.impl

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.usecase.akun.SaveAkun
import com.ra.bkuang.dummy.model.AkunDummy
import kotlinx.coroutines.runBlocking
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
