package com.ra.bkuang.domain.mapper

import com.ra.bkuang.data.local.entity.TransferEntity
import com.ra.bkuang.domain.model.TransferModel

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