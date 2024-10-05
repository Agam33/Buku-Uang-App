package com.ra.bkuang.features.account.data.mapper

import com.ra.bkuang.core.data.source.local.database.entity.AkunEntity
import com.ra.bkuang.features.account.data.model.AkunModel

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