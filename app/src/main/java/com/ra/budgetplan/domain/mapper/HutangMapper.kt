package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.HutangEntity
import com.ra.budgetplan.domain.model.HutangModel

object HutangMapper {
  fun hutangToEntity(hutangModel: HutangModel) = HutangEntity(
    hutangModel.uuid,
    hutangModel.idKategori,
    hutangModel.idAkun,
    hutangModel.idCicilan,
    hutangModel.jumlah,
    hutangModel.createdAt,
    hutangModel.updatedAt
  )

  fun hutangToModel(hutangEntity: HutangEntity) = HutangModel(
    hutangEntity.uuid,
    hutangEntity.idKategori,
    hutangEntity.idAkun,
    hutangEntity.idCicilan,
    hutangEntity.jumlah,
    hutangEntity.createdAt,
    hutangEntity.updatedAt
  )
}