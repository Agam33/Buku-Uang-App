package com.ra.bkuang.features.transaction.data.mapper

import com.ra.bkuang.features.transaction.data.entity.PengeluaranEntity
import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel

fun PengeluaranEntity.toModel(): PengeluaranModel = PengeluaranModel(
  uuid,
  idKategori,
  idAkun,
  deskripsi,
  jumlah,
  createdAt,
  updatedAt
)

fun PengeluaranModel.toEntity(): PengeluaranEntity = PengeluaranEntity(
  uuid,
  idKategori,
  idAkun,
  deskripsi,
  jumlah,
  createdAt,
  updatedAt
)