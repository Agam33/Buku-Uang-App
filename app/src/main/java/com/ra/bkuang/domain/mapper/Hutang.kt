package com.ra.bkuang.domain.mapper

import com.ra.bkuang.data.local.database.entity.HutangEntity
import com.ra.bkuang.data.local.database.entity.PembayaranHutangEntity
import com.ra.bkuang.domain.model.HutangModel
import com.ra.bkuang.domain.model.PembayaranHutangModel

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