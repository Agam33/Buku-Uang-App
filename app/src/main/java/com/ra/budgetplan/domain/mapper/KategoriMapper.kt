package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.KategoriEntity
import com.ra.budgetplan.domain.model.KategoriModel

fun KategoriModel.toEntity(): KategoriEntity = KategoriEntity(
  uuid,
  icUrl,
  icon,
  nama,
  tipeKategori,
  createdAt,
  updatedAt
)

fun KategoriEntity.toModel(): KategoriModel = KategoriModel(
  uuid,
  icUrl,
  icon,
  nama,
  tipeKategori,
  createdAt,
  updatedAt
)
