package com.ra.budgetplan.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.UUID

@Parcelize
data class HutangModel(
  var uuid: UUID,
  var idKategori: UUID,
  var idAkun: UUID,
  var idCicilan: UUID,
  var jumlah: Int,
  var createdAt: LocalDateTime,
  var updatedAt: LocalDateTime
) : Parcelable
