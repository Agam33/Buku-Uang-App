package com.ra.bkuang.domain.mapper

import com.ra.bkuang.data.entity.PengeluaranEntity
import com.ra.bkuang.domain.model.PengeluaranModel

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