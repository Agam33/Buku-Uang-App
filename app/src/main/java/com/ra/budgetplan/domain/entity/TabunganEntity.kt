package com.ra.budgetplan.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class TabunganEntity(
  @PrimaryKey val uuid: UUID,
  @ColumnInfo(name = "ic_url") val icUrl: String,
  @ColumnInfo(name = "nama") val nama: String,
  @ColumnInfo(name = "total") val total: String,
  @ColumnInfo(name = "created_at") val createdAt: LocalDateTime,
  @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime
)