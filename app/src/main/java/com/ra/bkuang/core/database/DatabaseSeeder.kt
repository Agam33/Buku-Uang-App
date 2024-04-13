package com.ra.bkuang.core.database

import android.annotation.SuppressLint
import com.ra.bkuang.core.database.dao.KategoriDao
import com.ra.bkuang.features.category.data.entity.KategoriEntity
import com.ra.bkuang.features.transaction.data.entity.TransactionType
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
        transactionType = TransactionType.PENGELUARAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Perbaikan",
        transactionType = TransactionType.PENGELUARAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Keperluan Bayi",
        transactionType = TransactionType.PENGELUARAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Kopi",
        transactionType = TransactionType.PENGELUARAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Bayar Listrik",
        transactionType = TransactionType.PENGELUARAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Makanan",
        transactionType = TransactionType.PENGELUARAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Kesehatan",
        transactionType = TransactionType.PENGELUARAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Pendidikan",
        transactionType = TransactionType.PENGELUARAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Wifi",
        transactionType = TransactionType.PENGELUARAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Belanja",
        transactionType = TransactionType.PENGELUARAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    // Income
    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Pekerjaan",
        transactionType = TransactionType.PENDAPATAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Bermain Game",
        transactionType = TransactionType.PENDAPATAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Bertanam",
        transactionType = TransactionType.PENDAPATAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Bisnis",
        transactionType = TransactionType.PENDAPATAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Youtube",
        transactionType = TransactionType.PENDAPATAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Ngojek",
        transactionType = TransactionType.PENDAPATAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Trading",
        transactionType = TransactionType.PENDAPATAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
        KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Hadiah Juara",
        transactionType = TransactionType.PENDAPATAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
      KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Rekaman",
        transactionType = TransactionType.PENDAPATAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    categories.add(
      KategoriEntity(
        uuid = UUID.randomUUID(),
        nama = "Jualan",
        transactionType = TransactionType.PENDAPATAN,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now()
      )
    )

    scope.launch {
      kategoriDao.saveAll(categories)
    }
  }
}