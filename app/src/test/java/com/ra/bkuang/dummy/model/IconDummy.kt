package com.ra.bkuang.dummy.model

import com.ra.bkuang.customview.dialog.icon.IconCategory
import com.ra.bkuang.data.local.entity.IconEntity
import java.util.UUID

object IconDummy {
  private val listIcon = listOf(
    IconEntity(
      UUID.randomUUID(),
      IconCategory.EXPENSE,
      -1,
    ),
    IconEntity(
      UUID.randomUUID(),
      IconCategory.ACCOUNT,
      -1,
    ),
    IconEntity(
      UUID.randomUUID(),
      IconCategory.INCOME,
      -1,
    ),
  )

  fun getAllIcon(): List<IconEntity> = listIcon
}