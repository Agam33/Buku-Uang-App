package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.TransferEntity
import com.ra.budgetplan.domain.model.TransferModel

fun transferToModel(
  transferEntity: TransferEntity
): TransferModel = TransferModel(
  transferEntity. uuid,
  transferEntity. idFromTabungan,
  transferEntity. idToTabungan,
  transferEntity. deskripsi,
  transferEntity. jumlah,
  transferEntity. createdAt,
  transferEntity. updatedAt
)

fun transferToEntity(
  transferModel: TransferModel
): TransferEntity = TransferEntity(
  transferModel. uuid,
  transferModel. idFromTabungan,
  transferModel. idToTabungan,
  transferModel. deskripsi,
  transferModel. jumlah,
  transferModel. createdAt,
  transferModel. updatedAt
)