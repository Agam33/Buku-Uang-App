package com.ra.bkuang.features.transaction.data.mapper

import com.ra.bkuang.features.account.data.mapper.toEntity
import com.ra.bkuang.features.account.data.mapper.toModel
import com.ra.bkuang.features.category.data.mapper.toEntity
import com.ra.bkuang.features.category.data.mapper.toModel
import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import com.ra.bkuang.features.transaction.data.entity.DetailPengeluaran
import com.ra.bkuang.features.transaction.data.entity.DetailTransfer
import com.ra.bkuang.features.transaction.domain.model.DetailPendapatanModel
import com.ra.bkuang.features.transaction.domain.model.DetailPengeluaranModel
import com.ra.bkuang.features.transaction.domain.model.DetailTransferModel

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