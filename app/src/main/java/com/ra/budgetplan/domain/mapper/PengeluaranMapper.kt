package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.PengeluaranEntity
import com.ra.budgetplan.domain.model.PengeluaranModel

object PengeluaranMapper {

  fun pengeluaranToModel(
    pengeluaranEntity: PengeluaranEntity
  ): PengeluaranModel = PengeluaranModel(
    pengeluaranEntity.uuid,
    pengeluaranEntity.idKategori,
    pengeluaranEntity.idAkun,
    pengeluaranEntity.deskripsi,
    pengeluaranEntity.jumlah,
    pengeluaranEntity.createdAt,
    pengeluaranEntity.updatedAt
  )

  fun pengeluaranToEntity(
    pengeluaranModel: PengeluaranModel
  ): PengeluaranEntity = PengeluaranEntity(
    pengeluaranModel.uuid,
    pengeluaranModel.idKategori,
    pengeluaranModel.idAkun,
    pengeluaranModel.deskripsi,
    pengeluaranModel.jumlah,
    pengeluaranModel.createdAt,
    pengeluaranModel.updatedAt
  )
}