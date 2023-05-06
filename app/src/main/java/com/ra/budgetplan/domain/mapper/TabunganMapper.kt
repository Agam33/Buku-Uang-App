package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.TabunganEntity
import com.ra.budgetplan.domain.model.TabunganModel

fun tabunganToModel(
  tabunganEntity: TabunganEntity
): TabunganModel = TabunganModel(
  tabunganEntity.uuid,
  tabunganEntity.icUrl,
  tabunganEntity.nama,
  tabunganEntity.total,
  tabunganEntity.createdAt,
  tabunganEntity.updatedAt
)

fun tabunganToEntity(
  tabunganModel: TabunganModel
): TabunganEntity = TabunganEntity(
  tabunganModel.uuid,
  tabunganModel.icUrl,
  tabunganModel.nama,
  tabunganModel.total,
  tabunganModel.createdAt,
  tabunganModel.updatedAt
)