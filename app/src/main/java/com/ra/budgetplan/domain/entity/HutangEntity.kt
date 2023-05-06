package com.ra.budgetplan.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity(
  tableName = "cicilan_tbl",
  foreignKeys = [
    ForeignKey(
      entity = KategoriEntity::class,
      parentColumns = ["uuid"],
      childColumns = ["id_kategori"]
    )
  ]
)
data class HutangEntity(
  @PrimaryKey val uuid: UUID,
  @ColumnInfo(name = "id_kategori") val idKategori: UUID,
  @ColumnInfo(name = "deskripsi") val deskripsi: String,
  @ColumnInfo(name = "total_pengeluaran") val totalPengeluaran: Int,
  @ColumnInfo(name = "max_cicilan") val maxCicilan: Int,
  @ColumnInfo(name = "id_pengingat") val idPengingat: Int,
  @ColumnInfo(name = "jatuh_tempo") val jatuhTempo: LocalDate,
  @ColumnInfo(name = "created_at") val createdAt: LocalDateTime,
  @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime
)
