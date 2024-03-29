package com.ra.bkuang.domain.mapper

import com.ra.bkuang.data.local.database.entity.PendapatanEntity
import com.ra.bkuang.domain.model.PendapatanModel

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
