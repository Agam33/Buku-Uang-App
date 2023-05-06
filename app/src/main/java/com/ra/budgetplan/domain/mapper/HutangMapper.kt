package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.HutangEntity
import com.ra.budgetplan.domain.model.HutangModel

fun cicilanToModel(
  hutangEntity: HutangEntity
): HutangModel = HutangModel(
  hutangEntity.uuid,
  hutangEntity.idKategori,
  hutangEntity.deskripsi,
  hutangEntity.totalPengeluaran,
  hutangEntity. maxCicilan,
  hutangEntity. idPengingat,
  hutangEntity. jatuhTempo,
  hutangEntity. createdAt,
  hutangEntity. updatedAt
)

fun cicilanToEntity(
  hutangModel: HutangModel
): HutangEntity = HutangEntity(
  hutangModel.uuid,
  hutangModel.idKategori,
  hutangModel.deskripsi,
  hutangModel.totalPengeluaran,
  hutangModel. maxCicilan,
  hutangModel. idPengingat,
  hutangModel. jatuhTempo,
  hutangModel. createdAt,
  hutangModel. updatedAt
)