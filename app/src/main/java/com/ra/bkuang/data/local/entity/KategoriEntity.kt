package com.ra.bkuang.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity
data class KategoriEntity(
  @PrimaryKey val uuid: UUID,
  @ColumnInfo(name = "nama") val nama: String,
  @ColumnInfo(name = "tipe_kategori") val tipeKategori: TipeKategori,
  @ColumnInfo(name = "created_at") val createdAt: LocalDateTime,
  @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime
)
