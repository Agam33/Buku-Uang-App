package com.ra.bkuang.features.category.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import java.time.LocalDateTime
import java.util.*

@Entity
data class KategoriEntity(
  @PrimaryKey val uuid: UUID,
  @ColumnInfo(name = "nama") val nama: String,
  @ColumnInfo(name = "tipe_kategori") val transactionType: TransactionType,
  @ColumnInfo(name = "created_at") val createdAt: LocalDateTime,
  @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime
)
