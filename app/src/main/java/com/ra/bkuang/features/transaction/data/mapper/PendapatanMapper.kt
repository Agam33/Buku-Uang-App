package com.ra.bkuang.features.transaction.data.mapper

import com.ra.bkuang.core.data.source.local.database.entity.PendapatanEntity
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel

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
