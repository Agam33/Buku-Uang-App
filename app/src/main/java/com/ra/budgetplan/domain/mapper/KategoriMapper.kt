package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.KategoriEntity
import com.ra.budgetplan.domain.model.KategoriModel

fun kategoriToModel(
  kategoriEntity: KategoriEntity
): KategoriModel = KategoriModel(
  kategoriEntity.uuid,
  kategoriEntity.icUrl,
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
  kategoriModel.nama,
  kategoriModel.tipeKategori,
  kategoriModel.createdAt,
  kategoriModel.updatedAt
)