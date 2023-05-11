package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.TransferEntity
import com.ra.budgetplan.domain.model.TransferModel

object TransferMapper {

  fun transferToModel(
    transferEntity: TransferEntity
  ): TransferModel = TransferModel(
    transferEntity. uuid,
    transferEntity. idFromAkun,
    transferEntity. idToAkun,
    transferEntity. deskripsi,
    transferEntity. jumlah,
    transferEntity. createdAt,
    transferEntity. updatedAt
  )

  fun transferToEntity(
    transferModel: TransferModel
  ): TransferEntity = TransferEntity(
    transferModel. uuid,
    transferModel. idFromAkun,
    transferModel. idToAkun,
    transferModel. deskripsi,
    transferModel. jumlah,
    transferModel. createdAt,
    transferModel. updatedAt
  )
}