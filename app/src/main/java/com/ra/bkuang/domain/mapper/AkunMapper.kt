package com.ra.bkuang.domain.mapper

import com.ra.bkuang.domain.entity.AkunEntity
import com.ra.bkuang.domain.model.AkunModel

fun AkunEntity.toModel(): AkunModel = AkunModel(
  uuid,
  icUrl,
  icon,
  nama,
  total,
  createdAt,
  updatedAt
)

fun AkunModel.toEntity(): AkunEntity = AkunEntity(
  uuid,
  icUrl,
  icon,
  nama,
  total,
  createdAt,
  updatedAt
)