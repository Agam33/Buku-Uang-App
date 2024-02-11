package com.ra.bkuang.domain.mapper

import com.ra.bkuang.data.local.entity.AkunEntity
import com.ra.bkuang.domain.model.AkunModel

fun AkunEntity.toModel(): AkunModel = AkunModel(
  uuid,
  nama,
  total,
  createdAt,
  updatedAt
)

fun AkunModel.toEntity(): AkunEntity = AkunEntity(
  uuid,
  nama,
  total,
  createdAt,
  updatedAt
)