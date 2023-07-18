package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.AkunEntity
import com.ra.budgetplan.domain.model.AkunModel

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