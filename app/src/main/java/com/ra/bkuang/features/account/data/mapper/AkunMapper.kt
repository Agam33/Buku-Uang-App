package com.ra.bkuang.features.account.data.mapper

import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.account.data.entity.AkunEntity

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