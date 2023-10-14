package com.ra.bkuang.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.UUID

@Parcelize
data class PembayaranHutangModel(
  var uuid: UUID,
  var idAkun: UUID,
  var idHutang: UUID,
  var jumlah: Int,
  var createdAt: LocalDateTime,
  var updatedAt: LocalDateTime
) : Parcelable {

  fun copy(): PembayaranHutangModel {
    return PembayaranHutangModel(
      uuid,
      idAkun,
      idHutang,
      jumlah,
      createdAt,
      updatedAt
    )
  }

}

@Parcelize
data class DetailPembayaranHutangModel(
  var pembayaranHutangModel: PembayaranHutangModel,
  var akunModel: AkunModel,
  var hutangModel: HutangModel
) : Parcelable