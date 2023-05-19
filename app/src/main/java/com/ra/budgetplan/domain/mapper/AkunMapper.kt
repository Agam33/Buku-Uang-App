package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.AkunEntity
import com.ra.budgetplan.domain.model.AkunModel

object AkunMapper {
  fun akunToModel(
    akunEntity: AkunEntity
  ): AkunModel = AkunModel(
    akunEntity.uuid,
    akunEntity.icUrl,
    akunEntity.icon,
    akunEntity.nama,
    akunEntity.total,
    akunEntity.createdAt,
    akunEntity.updatedAt
  )

  fun akunToEntity(
    akunModel: AkunModel
  ): AkunEntity = AkunEntity(
    akunModel.uuid,
    akunModel.icUrl,
    akunModel.icon,
    akunModel.nama,
    akunModel.total,
    akunModel.createdAt,
    akunModel.updatedAt
  )
}