package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.KategoriEntity
import com.ra.budgetplan.domain.model.KategoriModel

object KategoriMapper {
  fun kategoriToModel(
    kategoriEntity: KategoriEntity
  ): KategoriModel = KategoriModel(
    kategoriEntity.uuid,
    kategoriEntity.icUrl,
    kategoriEntity.icon,
    kategoriEntity.nama,
    kategoriEntity.tipeKategori,
    kategoriEntity.createdAt,
    kategoriEntity.updatedAt
  )

  fun kategoriToEntity(
    kategoriModel: KategoriModel
  ): KategoriEntity = KategoriEntity(
    kategoriModel.uuid,
    kategoriModel.icUrl,
    kategoriModel.icon,
    kategoriModel.nama,
    kategoriModel.tipeKategori,
    kategoriModel.createdAt,
    kategoriModel.updatedAt
  )
}
