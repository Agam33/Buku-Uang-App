package com.ra.bkuang.domain.mapper

import com.ra.bkuang.domain.entity.IconEntity
import com.ra.bkuang.domain.model.IconModel

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