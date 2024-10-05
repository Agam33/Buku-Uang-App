package com.ra.bkuang.features.category.data.mapper

import com.ra.bkuang.core.data.source.local.database.entity.KategoriEntity
import com.ra.bkuang.features.category.data.model.KategoriModel

fun KategoriModel.toEntity(): KategoriEntity = KategoriEntity(
  uuid,
  nama,
  tipeKategori,
  createdAt,
  updatedAt
)

fun KategoriEntity.toModel(): KategoriModel = KategoriModel(
  uuid,
  nama,
  transactionType,
  createdAt,
  updatedAt
)
