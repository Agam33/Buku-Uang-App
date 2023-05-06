package com.ra.budgetplan.domain.model

import android.os.Parcelable
import com.ra.budgetplan.domain.entity.TipeKategori
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.*

@Parcelize
data class KategoriModel(
  var uuid: UUID,
  var icUrl: String,
  var nama: String,
  var tipeKategori: TipeKategori,
  var createdAt: LocalDateTime,
  var updatedAt: LocalDateTime
) : Parcelable
