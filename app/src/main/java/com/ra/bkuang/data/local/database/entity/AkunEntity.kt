package com.ra.bkuang.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class AkunEntity(
  @PrimaryKey val uuid: UUID,
  @ColumnInfo(name = "nama") val nama: String,
  @ColumnInfo(name = "total") val total: Int,
  @ColumnInfo(name = "created_at") val createdAt: LocalDateTime,
  @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime
)