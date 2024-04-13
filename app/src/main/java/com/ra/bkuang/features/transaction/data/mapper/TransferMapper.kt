package com.ra.bkuang.features.transaction.data.mapper

import com.ra.bkuang.features.transaction.data.entity.TransferEntity
import com.ra.bkuang.features.transaction.domain.model.TransferModel

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