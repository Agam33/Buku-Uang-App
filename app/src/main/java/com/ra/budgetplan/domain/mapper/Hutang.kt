package com.ra.budgetplan.domain.mapper

import com.ra.budgetplan.domain.entity.HutangEntity
import com.ra.budgetplan.domain.entity.PembayaranHutangEntity
import com.ra.budgetplan.domain.model.HutangModel
import com.ra.budgetplan.domain.model.PembayaranHutangModel

fun HutangEntity.toModel(): HutangModel {
  return HutangModel(
    uuid,
    nama,
    deskripsi,
    totalPengeluaran,
    maxCicilan,
    idPengingat,
    pengingatAktif,
    tglPengingat,
    jatuhTempo,
    createdAt,
    updatedAt
  )
}

fun HutangModel.toEntity(): HutangEntity {
  return HutangEntity(
    uuid,
    nama,
    deskripsi,
    totalPengeluaran,
    maxCicilan,
    idPengingat,
    pengingatAktif,
    tglPengingat,
    jatuhTempo,
    createdAt,
    updatedAt
  )
}

fun PembayaranHutangEntity.toModel(): PembayaranHutangModel {
  return PembayaranHutangModel(
    uuid,
    idAkun,
    idHutang,
    jumlah,
    createdAt,
    updatedAt
  )
}

fun PembayaranHutangModel.toEntity(): PembayaranHutangEntity {
  return PembayaranHutangEntity(
    uuid,
    idAkun,
    idHutang,
    jumlah,
    createdAt,
    updatedAt
  )
}