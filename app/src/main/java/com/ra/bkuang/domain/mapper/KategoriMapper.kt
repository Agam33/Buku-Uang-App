package com.ra.bkuang.domain.mapper

import com.ra.bkuang.data.entity.KategoriEntity
import com.ra.bkuang.domain.model.KategoriModel

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
