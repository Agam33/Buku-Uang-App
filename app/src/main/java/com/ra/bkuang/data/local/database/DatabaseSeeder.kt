package com.ra.bkuang.data.local.database

import android.annotation.SuppressLint
import com.ra.bkuang.data.local.database.dao.KategoriDao
import com.ra.bkuang.data.local.database.entity.KategoriEntity
import com.ra.bkuang.data.local.database.entity.TipeKategori.*
import com.ra.bkuang.util.Utils.coroutineIOThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID

class DatabaseSeeder(
  private val kategoriDao: KategoriDao,
  private val scope: CoroutineScope
) {

  @SuppressLint("DiscouragedApi")
  fun seedDataCategory() {
    val categories = mutableListOf<KategoriEntity>()

    // Expense
    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Ulang Tahun",
        tipeKategori = PENGELUARAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Perbaikan",
        tipeKategori = PENGELUARAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Keperluan Bayi",
        tipeKategori = PENGELUARAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Kopi",
        tipeKategori = PENGELUARAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Bayar Listrik",
        tipeKategori = PENGELUARAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Makanan",
        tipeKategori = PENGELUARAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Kesehatan",
        tipeKategori = PENGELUARAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Pendidikan",
        tipeKategori = PENGELUARAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Wifi",
        tipeKategori = PENGELUARAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
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
        nama = "Pekerjaan",
        tipeKategori = PENDAPATAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Bermain Game",
        tipeKategori = PENDAPATAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Bertanam",
        tipeKategori = PENDAPATAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Bisnis",
        tipeKategori = PENDAPATAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Youtube",
        tipeKategori = PENDAPATAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Ngojek",
        tipeKategori = PENDAPATAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Trading",
        tipeKategori = PENDAPATAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Hadiah Juara",
        tipeKategori = PENDAPATAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
      KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Rekaman",
        tipeKategori = PENDAPATAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
      KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Jualan",
        tipeKategori = PENDAPATAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    scope.launch {
      kategoriDao.saveAll(categories)
    }
  }
}