package com.ra.budgetplan.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.*

@Parcelize
data class PengeluaranModel(
  var uuid: UUID,
  var idKategori: UUID,
  var idTabungan: UUID,
  var deskripsi: String,
  var jumlah: Int,
  var createdAt: LocalDateTime,
  var updatedAt: LocalDateTime
) : Parcelable