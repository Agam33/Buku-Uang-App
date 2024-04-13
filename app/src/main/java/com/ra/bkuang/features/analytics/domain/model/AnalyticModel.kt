package com.ra.bkuang.features.analytics.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnalyticModel(
  var name: String = "",
  var total: Int = 0,
  var percent: Double = 0.0
) : Parcelable
