package com.ra.bkuang.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDateTime
import java.util.UUID

@Entity(
  tableName = "pendapatan_tbl",
  foreignKeys = [
    ForeignKey(
      entity = KategoriEntity::class,
      parentColumns = ["uuid"],
      childColumns = ["id_kategori"],
      onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
      entity = AkunEntity::class,
      parentColumns = ["uuid"],
      childColumns = ["id_tabungan"],
      onDelete = ForeignKey.CASCADE
    )
  ]
)
data class PendapatanEntity(
  @PrimaryKey val uuid: UUID,
  @ColumnInfo(name = "id_kategori", index = true) val idKategori: UUID,
  @ColumnInfo(name = "id_tabungan", index = true) val idTabungan: UUID,
  @ColumnInfo(name = "deskripsi") val deskripsi: String,
  @ColumnInfo(name = "jumlah") val jumlah: Int,
  @ColumnInfo(name = "created_at") val createdAt: LocalDateTime,
  @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime
)


data class DetailPendapatan(
  @Embedded val pendapatan: PendapatanEntity,

  @Relation(
    parentColumn = "id_kategori",
    entityColumn = "uuid"
  )
  val kategori: KategoriEntity,

  @Relation(
    parentColumn = "id_tabungan",
    entityColumn = "uuid"
  )
  val akun: AkunEntity,
)
