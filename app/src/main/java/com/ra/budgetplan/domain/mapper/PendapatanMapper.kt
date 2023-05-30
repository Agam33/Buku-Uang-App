package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.PendapatanEntity
import com.ra.budgetplan.domain.model.PendapatanModel

fun PendapatanModel.toEntity(): PendapatanEntity = PendapatanEntity(
  uuid,
  idKategori,
  idAkun,
  deskripsi,
  jumlah,
  createdAt,
  updatedAt
)

fun PendapatanEntity.toModel(): PendapatanModel = PendapatanModel(
  uuid,
  idKategori,
  idTabungan,
  deskripsi,
  jumlah,
  createdAt,
  updatedAt
)
