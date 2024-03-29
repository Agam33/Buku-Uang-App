package com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.data.local.database.entity.DetailPengeluaran
import com.ra.bkuang.domain.repository.PengeluaranRepository
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.GetMonthlyPengeluaran
import com.ra.bkuang.dummy.model.AkunDummy
import com.ra.bkuang.dummy.model.KategoriDummy
import com.ra.bkuang.dummy.model.PengeluaranDummy
import com.ra.bkuang.domain.model.RvGroup
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class GetMonthlyPengeluaranImplTest {
  private lateinit var getMonthlyPengeluaran: GetMonthlyPengeluaran
  private val pengeluaranRepository: PengeluaranRepository = mock()

  @Test
  fun `GetMonthlyPengeluaran, should success`() = runBlocking {
    getMonthlyPengeluaran = GetMonthlyPengeluaranImpl(pengeluaranRepository)

    val actualPengeluaran = PengeluaranDummy.getAllPengeluaran()[0]
    val actualKategori = KategoriDummy.getAllKategori()[0]
    val actualAkun = AkunDummy.getAllAccounts()[0]

    val actualDetail1 = DetailPengeluaran(
      akun = actualAkun,
      kategori = actualKategori,
      pengeluaran = actualPengeluaran
    )

    val actualDetail2 = DetailPengeluaran(
      akun = actualAkun,
      kategori = actualKategori,
      pengeluaran = actualPengeluaran
    )

    val monthVal = 8
    val yearVal = 2023

    val actualListDetail = listOf(actualDetail1, actualDetail2)

    val monthly = RvGroup<String, ArrayList<DetailPengeluaran>>()
    for (data in actualListDetail) {
      val updatedAt = data.pengeluaran.createdAt
      val key = updatedAt.toLocalDate().toString()
      monthly.addIf(key, ArrayList())?.add(data)
    }

    whenever(pengeluaranRepository.getMonthlyPengeluaran(any(), any()))
      .thenReturn(flow { emit(actualListDetail) })

    getMonthlyPengeluaran.invoke(monthVal, yearVal).test {
      val expectedVal = awaitItem().data
      assertNotNull(expectedVal)
      assertEquals(monthly, expectedVal)
      assertEquals(monthly.size, expectedVal?.size)
      awaitComplete()
    }
  }
}