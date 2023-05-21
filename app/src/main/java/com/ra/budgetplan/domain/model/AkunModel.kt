package com.ra.budgetplan.domain.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.UUID

@Parcelize
data class AkunModel(
  var uuid: UUID,
  var icUrl: String = "",
  var icon: Int,
  var nama: String,
  var total: Int,
  var createdAt: LocalDateTime,
  var updatedAt: LocalDateTime
) : Parcelable
