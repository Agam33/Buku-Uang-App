package com.ra.bkuang.features.debt.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.*

@Parcelize
data class HutangModel(
  var uuid: UUID,
  var nama: String,
  var deskripsi: String,
  var totalPengeluaran: Int,
  var maxCicilan: Int,
  var idPengingat: Int,
  var pengingatAktif: Boolean,
  var tglPengingat: String,
  var jatuhTempo: LocalDateTime,
  var createdAt: LocalDateTime,
  var updatedAt: LocalDateTime
) : Parcelable
