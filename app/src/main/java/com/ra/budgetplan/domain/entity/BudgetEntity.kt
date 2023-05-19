package com.ra.budgetplan.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

@Entity(
  tableName = "budget_tbl",
  foreignKeys = [
    ForeignKey(
      entity = KategoriEntity::class,
      parentColumns = ["uuid"],
      childColumns = ["id_kategori"]
    )
  ]
)
data class BudgetEntity(
  @PrimaryKey val uuid: UUID,
  @ColumnInfo(name = "id_kategori", index = true) val idKategori: UUID,
  @ColumnInfo(name = "deskripsi") val deskripsi: String,
  @ColumnInfo(name = "pengeluaran") val pengeluaran: Int,
  @ColumnInfo(name = "max_pengeluaran") val maxPengeluaran: Int,
  @ColumnInfo(name = "created_at") val createdAt: LocalDateTime,
  @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime
)
