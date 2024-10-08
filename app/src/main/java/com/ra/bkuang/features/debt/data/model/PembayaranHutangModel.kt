package com.ra.bkuang.features.debt.data.model

import android.os.Parcelable
import com.ra.bkuang.features.account.data.model.AkunModel
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