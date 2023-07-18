package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.HutangEntity
import com.ra.budgetplan.domain.model.HutangModel

fun HutangEntity.toModel() = HutangModel(
  uuid,
  idKategori,
  idAkun,
  idCicilan,
  jumlah,
  createdAt,
  updatedAt
)

fun HutangModel.toEntity() = HutangEntity(
  uuid,
  idKategori,
  idAkun,
  idCicilan,
  jumlah,
  createdAt,
  updatedAt
)