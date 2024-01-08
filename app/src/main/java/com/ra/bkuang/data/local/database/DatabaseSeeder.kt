package com.ra.bkuang.data.local.database

import android.annotation.SuppressLint
import com.ra.bkuang.data.local.database.dao.KategoriDao
import com.ra.bkuang.data.local.entity.KategoriEntity
import com.ra.bkuang.data.local.entity.TipeKategori.*
import com.ra.bkuang.presentation.util.Constants.coroutineIOThread
import java.time.LocalDateTime
import java.util.UUID

class DatabaseSeeder(
  private val kategoriDao: KategoriDao,
) {

  @SuppressLint("DiscouragedApi")
  fun seedDataCategory() {
    val categories = mutableListOf<KategoriEntity>()

    // Expense
    categories.add(
      KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon = -1,
      nama = "Ulang Tahun",
      tipeKategori = PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
    )

    categories.add(
      KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon = -1,
      nama = "Perbaikan",
      tipeKategori = PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
    )

    categories.add(
      KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =  -1,
      nama = "Keperluan Bayi",
      tipeKategori = PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
    )

    categories.add(
      KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon = -1,
      nama = "Kopi",
      tipeKategori = PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
    )

    categories.add(
      KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon = -1,
      nama = "Bayar Listrik",
      tipeKategori = PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
    )

    categories.add(
      KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =  -1,
      nama = "Makanan",
      tipeKategori = PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
    )

    categories.add(
      KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =-1,
      nama = "Kesehatan",
      tipeKategori = PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
    )

    categories.add(
      KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon = -1,
      nama = "Pendidikan",
      tipeKategori = PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
    )

    categories.add(
      KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon = -1,
      nama = "Wifi",
      tipeKategori = PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
    )

    categories.add(
      KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =-1,
      nama = "Belanja",
      tipeKategori = PENGELUARAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
    )

    // Income
    categories.add(
      KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =-1,
      nama = "Pekerjaan",
      tipeKategori = PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
    )

    categories.add(
      KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =-1,
      nama = "Bermain Game",
      tipeKategori = PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
    )

    categories.add(
      KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =-1,
      nama = "Bertanam",
      tipeKategori = PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
    )

    categories.add(
      KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =-1,
      nama = "Bisnis",
      tipeKategori = PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
    )

    categories.add(
      KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =-1,
      nama = "Youtube",
      tipeKategori = PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
    )

    categories.add(
      KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =-1,
      nama = "Ngojek",
      tipeKategori = PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
    )

    categories.add(
      KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =-1,
      nama = "Trading",
      tipeKategori = PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
    )

    categories.add(
      KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =-1,
      nama = "Hadiah Juara",
      tipeKategori = PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
    )

    categories.add(
      KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =-1,
      nama = "Rekaman",
      tipeKategori = PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
    )

    categories.add(
      KategoriEntity(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon =-1,
      nama = "Jualan",
      tipeKategori = PENDAPATAN,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )
    )

    coroutineIOThread {
      kategoriDao.saveAll(categories)
    }
  }
}