package com.ra.bkuang.core.data.source.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity(
  tableName = "budget_tbl",
  foreignKeys = [
    ForeignKey(
      entity = KategoriEntity::class,
      parentColumns = ["uuid"],
      childColumns = ["id_kategori"],
      onDelete = ForeignKey.CASCADE
    )
  ]
)
data class BudgetEntity(
  @PrimaryKey val uuid: UUID,
  @ColumnInfo(name = "id_kategori", index = true) val idKategori: UUID,
  @ColumnInfo(name = "deskripsi") val deskripsi: String,
  @ColumnInfo(name = "pengeluaran") val pengeluaran: Int,
  @ColumnInfo(name = "max_pengeluaran") val maxPengeluaran: Int,
  @ColumnInfo(name = "bulan_tahun") val bulanTahun: LocalDate,
  @ColumnInfo(name = "created_at") val createdAt: LocalDateTime,
  @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime
)

data class DetailBudget(
    @Embedded val budget: BudgetEntity,

    @Relation(
    parentColumn = "id_kategori",
    entityColumn = "uuid"
  )
  val kategoriEntity: KategoriEntity
)