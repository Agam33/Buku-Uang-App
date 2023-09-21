package com.ra.budgetplan.dummy.model

import com.ra.budgetplan.domain.entity.KategoriEntity
import com.ra.budgetplan.domain.entity.TipeKategori
import java.time.LocalDateTime
import java.util.UUID

object KategoriDummy {
  private val listKategori = listOf(
    KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon = -1,
      nama = "Ulang Tahun",
      tipeKategori = TipeKategori.PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ),
    KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon = -1,
      nama = "Perbaikan",
      tipeKategori = TipeKategori.PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ),
    KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =-1,
      nama = "Bertanam",
      tipeKategori = TipeKategori.PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    ),
    KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =-1,
      nama = "Youtube",
      tipeKategori = TipeKategori.PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
  )

  fun getAllKategori(): List<KategoriEntity> = listKategori
}