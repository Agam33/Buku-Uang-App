package com.ra.bkuang.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDateTime
import java.util.UUID

@Entity(
  tableName = "pembayaran_hutang_tbl",
  foreignKeys = [
    ForeignKey(
      entity = AkunEntity::class,
      parentColumns = ["uuid"],
      childColumns = ["id_akun"],
      onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
      entity = HutangEntity::class,
      parentColumns = ["uuid"],
      childColumns = ["id_hutang"],
      onDelete = ForeignKey.CASCADE
    )
  ]
)
data class PembayaranHutangEntity(
  @PrimaryKey val uuid: UUID,
  @ColumnInfo(name = "id_akun", index = true) val idAkun: UUID,
  @ColumnInfo(name = "id_hutang", index = true) val idHutang: UUID,
  @ColumnInfo(name = "jumlah") val jumlah: Int,
  @ColumnInfo(name = "created_at") val createdAt: LocalDateTime,
  @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime
)

data class DetailPembayaranHutang(
    @Embedded
  val pembayaranHutang: PembayaranHutangEntity,

    @Relation(
    parentColumn = "id_akun",
    entityColumn = "uuid"
  ) val akun: AkunEntity,

    @Relation(
    parentColumn = "id_hutang",
    entityColumn = "uuid"
  ) val hutangEntity: HutangEntity
)