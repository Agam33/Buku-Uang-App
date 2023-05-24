package com.ra.budgetplan.domain.model

import com.ra.budgetplan.customview.dialog.icon.IconCategory
import java.util.UUID

data class IconModel(
  var uuid: UUID,
  var category: IconCategory,
  var icon: Int
)
