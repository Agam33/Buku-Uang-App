package com.ra.bkuang.domain.model

import android.os.Parcelable
import com.ra.bkuang.domain.entity.TipeKategori
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.*

@Parcelize
data class KategoriModel(
  var uuid: UUID,
  var icUrl: String = "",
  var icon: Int,
  var nama: String,
  var tipeKategori: TipeKategori,
  var createdAt: LocalDateTime,
  var updatedAt: LocalDateTime
) : Parcelable
