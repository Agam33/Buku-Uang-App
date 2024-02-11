package com.ra.bkuang.domain.mapper

import com.ra.bkuang.data.local.entity.KategoriEntity
import com.ra.bkuang.domain.model.KategoriModel

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
  tipeKategori,
  createdAt,
  updatedAt
)
