package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.CicilanEntity
import com.ra.budgetplan.domain.model.CicilanModel

object CicilanMapper {

  fun hutangToEntity(
    cicilanModel: CicilanModel
  ): CicilanEntity = CicilanEntity(
    cicilanModel.uuid,
    cicilanModel.deskripsi,
    cicilanModel.totalPengeluaran,
    cicilanModel. maxCicilan,
    cicilanModel. idPengingat,
    cicilanModel. jatuhTempo,
    cicilanModel. createdAt,
    cicilanModel. updatedAt
  )

  fun hutangToModel(
    cicilanEntity: CicilanEntity
  ): CicilanModel = CicilanModel(
    cicilanEntity.uuid,
    cicilanEntity.deskripsi,
    cicilanEntity.totalPengeluaran,
    cicilanEntity. maxCicilan,
    cicilanEntity. idPengingat,
    cicilanEntity. jatuhTempo,
    cicilanEntity. createdAt,
    cicilanEntity. updatedAt
  )
}

