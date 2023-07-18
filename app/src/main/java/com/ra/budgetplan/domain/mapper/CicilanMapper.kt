package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.CicilanEntity
import com.ra.budgetplan.domain.model.CicilanModel

fun CicilanEntity.toModel(): CicilanModel = CicilanModel(
  uuid,
  deskripsi,
  totalPengeluaran,
  maxCicilan,
  idPengingat,
  jatuhTempo,
  createdAt,
  updatedAt
)

fun CicilanModel.toEntity(): CicilanEntity = CicilanEntity(
  uuid,
  deskripsi,
  totalPengeluaran,
  maxCicilan,
  idPengingat,
  jatuhTempo,
  createdAt,
  updatedAt
)

