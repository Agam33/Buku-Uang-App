package com.ra.bkuang.domain.model


import com.ra.bkuang.presentation.ui.features.transaction.TransactionDetail
import com.ra.bkuang.presentation.ui.features.transaction.TransactionType
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.UUID

@Parcelize
data class DetailPengeluaranModel(
  val pengeluaran: PengeluaranModel,
  val kategori: KategoriModel,
  val akun: AkunModel,
): TransactionDetail {
  override val uuid: UUID get() = pengeluaran.uuid
  override val transactionType: TransactionType get() = TransactionType.EXPENSE
  override val name1: String get()= kategori.nama
  override val name2: String get() = akun.nama
  override val icUrl1: Int get() = kategori.icon
  override val icUrl2: Int get() = akun.icon
  override val description: String get() = pengeluaran.deskripsi
  override val jumlah: Int get() = pengeluaran.jumlah
  override val createdAt: LocalDateTime get() = pengeluaran.createdAt
  override val updatedAt: LocalDateTime get() = pengeluaran.updatedAt
}

@Parcelize
data class DetailPendapatanModel(
  val pendapatan: PendapatanModel,
  val kategori: KategoriModel,
  val akun: AkunModel,
): TransactionDetail {
  override val uuid: UUID get() = pendapatan.uuid
  override val transactionType: TransactionType get() = TransactionType.INCOME
  override val name1: String get() = kategori.nama
  override val name2: String get() = akun.nama
  override val icUrl1: Int get() = kategori.icon
  override val icUrl2: Int get() = akun.icon
  override val description: String  get() = pendapatan.deskripsi
  override val jumlah: Int  get() = pendapatan.jumlah
  override val createdAt: LocalDateTime get() = pendapatan.createdAt
  override val updatedAt: LocalDateTime get() = pendapatan.updatedAt
}

@Parcelize
data class DetailTransferModel(
  val transfer: TransferModel,
  val fromAkun: AkunModel,
  val toAkun: AkunModel,
): TransactionDetail {
  override val uuid: UUID get() = transfer.uuid
  override val transactionType: TransactionType get() = TransactionType.TRANSFER
  override val name1: String get() = fromAkun.nama
  override val name2: String get() = toAkun.nama
  override val icUrl1: Int get() = fromAkun.icon
  override val icUrl2: Int get() = toAkun.icon
  override val description: String get() = transfer.deskripsi
  override val jumlah: Int get() = transfer.jumlah
  override val createdAt: LocalDateTime get() = transfer.createdAt
  override val updatedAt: LocalDateTime get() =  transfer.updatedAt
}