package com.ra.bkuang.features.account.data.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.UUID

@Parcelize
data class AkunModel(
  var uuid: UUID,
  var nama: String,
  var total: Int,
  var createdAt: LocalDateTime,
  var updatedAt: LocalDateTime
) : Parcelable
