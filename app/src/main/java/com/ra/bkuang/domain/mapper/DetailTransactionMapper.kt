package com.ra.bkuang.domain.mapper

import com.ra.bkuang.domain.entity.DetailPendapatan
import com.ra.bkuang.domain.entity.DetailPengeluaran
import com.ra.bkuang.domain.entity.DetailTransfer
import com.ra.bkuang.domain.model.DetailPendapatanModel
import com.ra.bkuang.domain.model.DetailPengeluaranModel
import com.ra.bkuang.domain.model.DetailTransferModel

fun DetailPengeluaran.toModel(): DetailPengeluaranModel {
  return DetailPengeluaranModel(
    pengeluaran.toModel(),
    kategori.toModel(),
    akun.toModel()
  )
}

fun DetailPengeluaranModel.toEntity(): DetailPengeluaran {
  return DetailPengeluaran(
    pengeluaran.toEntity(),
    kategori.toEntity(),
   akun.toEntity()
  )
}

fun DetailPendapatan.toModel(): DetailPendapatanModel {
  return DetailPendapatanModel(
    pendapatan.toModel(),
    kategori.toModel(),
    akun.toModel()
  )
}

fun DetailPendapatanModel.toEntity(): DetailPendapatan {
  return DetailPendapatan(
    pendapatan.toEntity(),
    kategori.toEntity(),
    akun.toEntity()
  )
}

fun DetailTransfer.toModel(): DetailTransferModel {
  return DetailTransferModel(
    transfer.toModel(),
    fromAkun.toModel(),
    toAkun.toModel()
  )
}