package com.ra.bkuang.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

@Entity(
  tableName = "hutang_tbl",
)
data class HutangEntity(
  @PrimaryKey val uuid: UUID,
  @ColumnInfo(name = "nama") val nama: String,
  @ColumnInfo(name = "deskripsi") val deskripsi: String,
  @ColumnInfo(name = "total_pengeluaran") val totalPengeluaran: Int,
  @ColumnInfo(name = "max_cicilan") val maxCicilan: Int,
  @ColumnInfo(name = "id_pengingat") val idPengingat: Int,
  @ColumnInfo(name = "pengingat_aktif") val pengingatAktif: Boolean,
  @ColumnInfo(name = "tanggal_pengingat") val tglPengingat: String,
  @ColumnInfo(name = "jatuh_tempo") val jatuhTempo: LocalDateTime,
  @ColumnInfo(name = "created_at") val createdAt: LocalDateTime,
  @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime
)