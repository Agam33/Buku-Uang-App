package com.ra.bkuang.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ra.bkuang.presentation.ui.customview.dialog.icon.IconCategory
import java.util.UUID

@Entity
data class IconEntity(
  @PrimaryKey val uuid: UUID,
  val category: IconCategory,
  val icon: Int
)
