package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.PengeluaranEntity
import com.ra.budgetplan.domain.model.PengeluaranModel

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