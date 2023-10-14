package com.ra.bkuang.domain.model

import com.ra.bkuang.customview.dialog.icon.IconCategory
import java.util.UUID

data class IconModel(
  var uuid: UUID,
  var category: IconCategory,
  var icon: Int
)
