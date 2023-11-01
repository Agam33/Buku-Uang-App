package com.ra.bkuang.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDateTime
import java.util.UUID


@Entity(
  tableName = "pengeluaran_tbl",
  foreignKeys = [
    ForeignKey(
      entity = KategoriEntity::class,
      parentColumns = ["uuid"],
      childColumns = ["id_kategori"],
      onDelete = CASCADE
    ),
    ForeignKey(
      entity = AkunEntity::class,
      parentColumns = ["uuid"],
      childColumns = ["id_akun"],
      onDelete = CASCADE
    )
  ]
)
data class PengeluaranEntity(
  @PrimaryKey val uuid: UUID,
  @ColumnInfo(name = "id_kategori", index = true) val idKategori: UUID,
  @ColumnInfo(name = "id_akun", index = true) val idAkun: UUID,
  @ColumnInfo(name = "deskripsi") val deskripsi: String,
  @ColumnInfo(name = "jumlah") val jumlah: Int,
  @ColumnInfo(name = "created_at") val createdAt: LocalDateTime,
  @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime
)

data class DetailPengeluaran(
  @Embedded val pengeluaran: PengeluaranEntity,

  @Relation(
    parentColumn = "id_kategori",
    entityColumn = "uuid"
  )
  val kategori: KategoriEntity,

  @Relation(
    parentColumn = "id_akun",
    entityColumn = "uuid"
  )
  val akun: AkunEntity,
)
