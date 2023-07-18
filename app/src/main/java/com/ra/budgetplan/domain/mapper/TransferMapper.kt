package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.TransferEntity
import com.ra.budgetplan.domain.model.TransferModel

fun TransferEntity.toModel(): TransferModel =
  TransferModel(
  uuid,
  idFromAkun,
  idToAkun,
  deskripsi,
  jumlah,
  createdAt,
  updatedAt
)

fun TransferModel.toEntity(): TransferEntity =
  TransferEntity(
    uuid,
    idFromAkun,
    idToAkun,
    deskripsi,
    jumlah,
    createdAt,
    updatedAt
)