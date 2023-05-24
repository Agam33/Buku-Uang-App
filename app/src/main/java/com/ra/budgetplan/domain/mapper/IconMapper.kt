package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.IconEntity
import com.ra.budgetplan.domain.model.IconModel

fun IconEntity.toModel(): IconModel {
  return IconModel(
    uuid = uuid,
    category = category,
    icon = icon
  )
}

fun IconModel.toEntity(): IconEntity {
  return IconEntity(
    uuid = uuid,
    category = category,
    icon = icon
  )
}