package com.ra.bkuang.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnalyticModel(
  var name: String = "",
  var total: Int = 0,
  var percent: Double = 0.0
) : Parcelable