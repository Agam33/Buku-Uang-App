package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.PendapatanEntity
import com.ra.budgetplan.domain.model.PendapatanModel
import com.ra.budgetplan.domain.model.PengeluaranModel

fun pendapatanToModel(
  pendapatanEntity: PendapatanEntity
): PendapatanModel = PendapatanModel(
  pendapatanEntity.uuid,
  pendapatanEntity. idKategori,
  pendapatanEntity. idTabungan,
  pendapatanEntity. deskripsi,
  pendapatanEntity. jumlah,
  pendapatanEntity. createdAt,
  pendapatanEntity. updatedAt
)

fun pendapatanToEntity(
  pendapatanModel: PendapatanModel
): PendapatanEntity = PendapatanEntity(
  pendapatanModel.uuid,
  pendapatanModel. idKategori,
  pendapatanModel. idTabungan,
  pendapatanModel. deskripsi,
  pendapatanModel. jumlah,
  pendapatanModel. createdAt,
  pendapatanModel. updatedAt
)