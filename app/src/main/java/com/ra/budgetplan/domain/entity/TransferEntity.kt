package com.ra.budgetplan.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

import java.time.LocalDateTime
import java.util.UUID

@Entity(
  tableName = "transfer_tbl",
  foreignKeys = [
    ForeignKey(
      entity = AkunEntity::class,
      parentColumns = ["uuid"],
      childColumns = ["id_from_akun"]
    ),
    ForeignKey(
      entity = AkunEntity::class,
      parentColumns = ["uuid"],
      childColumns = ["id_to_akun"]
    )
  ]
)
data class TransferEntity(
  @PrimaryKey val uuid: UUID,
  @ColumnInfo(name = "id_from_akun", index = true) val idFromAkun: UUID,
  @ColumnInfo(name = "id_to_akun", index = true) val idToAkun: UUID,
  @ColumnInfo(name = "deskripsi") val deskripsi: String,
  @ColumnInfo(name = "jumlah") val jumlah: Int,
  @ColumnInfo(name = "created_at") val createdAt: LocalDateTime,
  @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime
)