package com.ra.budgetplan.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID


@Entity(
  tableName = "pengeluaran_tbl",
  foreignKeys = [
    ForeignKey(
      entity = KategoriEntity::class,
      parentColumns = ["uuid"],
      childColumns = ["id_kategori"]
    ),
    ForeignKey(
      entity = TabunganEntity::class,
      parentColumns = ["uuid"],
      childColumns = ["id_tabungan"]
    )
  ]
)
data class PengeluaranEntity(
  @PrimaryKey val uuid: UUID,
  @ColumnInfo(name = "id_kategori") val idKategori: UUID,
  @ColumnInfo(name = "id_tabungan") val idTabungan: UUID,
  @ColumnInfo(name = "deskripsi") val deskripsi: String,
  @ColumnInfo(name = "jumlah") val jumlah: Int,
  @ColumnInfo(name = "created_at") val createdAt: LocalDateTime,
  @ColumnInfo(name = "created_at") val updatedAt: LocalDateTime
)
