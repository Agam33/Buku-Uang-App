package com.ra.bkuang.di.core.usecase

import com.ra.bkuang.domain.usecase.transaksi.GetTotalTransactionByDate
import com.ra.bkuang.domain.usecase.transaksi.GetTotalTransactionByDateImpl
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.DeletePendapatanById
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.FindDetailPendapatanById
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.GetPendapatanByDate
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.GetPendapatanById
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.GetTotalPendapatan
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.GetTotalPendapatanByDate
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.SavePendapatan
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.UpdatePendapatan
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.impl.DeletePendapatanByIdImpl
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.impl.FindDetailPendapatanByIdImpl
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.impl.GetPendapatanByDateImpl
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.impl.GetPendapatanByIdImpl
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.impl.GetTotalPendapatanByDateImpl
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.impl.GetTotalPendapatanImpl
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.impl.SavePendapatanImpl
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.impl.UpdatePendapatanImpl
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.DeletePengeluaranById
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.FindDetailPengeluaranById
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.GetMonthlyPengeluaran
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.GetPengeluaranByDate
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.GetPengeluaranById
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.GetTotalPengeluaran
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.GetTotalPengeluaranByDate
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.SavePengeluaran
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.UpdatePengeluaran
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl.DeletePengeluaranByIdImpl
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl.FindDetailPengeluaranByIdImpl
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl.GetMonthlyPengeluaranImpl
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl.GetPengeluaranByDateImpl
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl.GetPengeluaranByIdImpl
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl.GetTotalPengeluaranByDateImpl
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl.GetTotalPengeluaranImpl
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl.SavePengeluaranImpl
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl.UpdatePengeluaranImpl
import com.ra.bkuang.domain.usecase.transaksi.transfer.DeleteTransfer
import com.ra.bkuang.domain.usecase.transaksi.transfer.DeleteTransferById
import com.ra.bkuang.domain.usecase.transaksi.transfer.FindDetailTransferById
import com.ra.bkuang.domain.usecase.transaksi.transfer.GetTransferByDate
import com.ra.bkuang.domain.usecase.transaksi.transfer.GetTransferById
import com.ra.bkuang.domain.usecase.transaksi.transfer.SaveTransfer
import com.ra.bkuang.domain.usecase.transaksi.transfer.UpdateTransfer
import com.ra.bkuang.domain.usecase.transaksi.transfer.impl.DeleteTransferByIdImpl
import com.ra.bkuang.domain.usecase.transaksi.transfer.impl.DeleteTransferImpl
import com.ra.bkuang.domain.usecase.transaksi.transfer.impl.FindDetailTransferByIdImpl
import com.ra.bkuang.domain.usecase.transaksi.transfer.impl.GetTransferByDateImpl
import com.ra.bkuang.domain.usecase.transaksi.transfer.impl.GetTransferByIdImpl
import com.ra.bkuang.domain.usecase.transaksi.transfer.impl.SaveTransferImpl
import com.ra.bkuang.domain.usecase.transaksi.transfer.impl.UpdateTransferImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseTransaksiModule {

  @Binds
  @Singleton
  fun bindGetPendapatanById(getPendapatanByIdImpl: GetPendapatanByIdImpl): GetPendapatanById

  @Binds
  @Singleton
  fun bindGetPengeluyaranById(getPengeluaranByIdImpl: GetPengeluaranByIdImpl): GetPengeluaranById

  @Binds
  @Singleton
  fun bindGetTransferById(getTransferByIdImpl: GetTransferByIdImpl): GetTransferById

  @Binds
  @Singleton
  fun bindDeletePendapataById(deletePendapatanByIdImpl: DeletePendapatanByIdImpl): DeletePendapatanById

  @Binds
  @Singleton
  fun bindDeletePengeluaranById(deletePengeluaranByIdImpl: DeletePengeluaranByIdImpl): DeletePengeluaranById

  @Binds
  @Singleton
  fun bindDeleteTransferById(deleteTransferByIdImpl: DeleteTransferByIdImpl): DeleteTransferById

  @Binds
  @Singleton
  fun bindFindPengeluaranById(findPengeluaranByIdImpl: FindDetailPengeluaranByIdImpl): FindDetailPengeluaranById

  @Binds
  @Singleton
  fun bindFindPendapatanById(findPendapatanByIdImpl: FindDetailPendapatanByIdImpl): FindDetailPendapatanById

  @Binds
  @Singleton
  fun bindFindTransferById(findTransferByIdImpl: FindDetailTransferByIdImpl): FindDetailTransferById

  @Binds
  @Singleton
  fun bindGetTotalTransactionByDate(getTotalTransactionByDateImpl: GetTotalTransactionByDateImpl): GetTotalTransactionByDate

  @Binds
  @Singleton
  fun bindGetTotaPendapatanByDate(getTotalPendapatanByDateImpl: GetTotalPendapatanByDateImpl): GetTotalPendapatanByDate

  @Binds
  @Singleton
  fun bindGetTotalPengeluaranByDate(getTotalPengeluaranByDateImpl: GetTotalPengeluaranByDateImpl): GetTotalPengeluaranByDate

  @Binds
  @Singleton
  fun bindGetTotalPendapatan(getTotalPendapatanImpl: GetTotalPendapatanImpl): GetTotalPendapatan

  @Binds
  @Singleton
  fun bindGetTotalPengeluaran(getTotalPengeluaranImpl: GetTotalPengeluaranImpl): GetTotalPengeluaran

  @Binds
  @Singleton
  fun bindGetMonthlyPengeluaran(getMonthlyPengeluaranImpl: GetMonthlyPengeluaranImpl): GetMonthlyPengeluaran

  @Binds
  @Singleton
  fun bindGetPengeluaranByDate(getPengeluaranByDateImpl: GetPengeluaranByDateImpl): GetPengeluaranByDate

  @Binds
  @Singleton
  fun bindGetPendapatanByDate(getPendapatanByDateImpl: GetPendapatanByDateImpl): GetPendapatanByDate

  @Binds
  @Singleton
  fun bindGetTransferByDate(getTransferImpl: GetTransferByDateImpl): GetTransferByDate

  @Binds
  @Singleton
  fun bindSavePengeluaran(pengeluaranImpl: SavePengeluaranImpl): SavePengeluaran

  @Binds
  @Singleton
  fun bindSavePendapatan(pendapatanImpl: SavePendapatanImpl): SavePendapatan

  @Binds
  @Singleton
  fun bindSaveTransfer(saveTransferImpl: SaveTransferImpl): SaveTransfer

  @Binds
  @Singleton
  fun bindUpdateTransfer(updateTransferImpl: UpdateTransferImpl): UpdateTransfer

  @Binds
  @Singleton
  fun bindUpdatePengeluaran(updatePengeluaranImpl: UpdatePengeluaranImpl): UpdatePengeluaran

  @Binds
  @Singleton
  fun bindUpdatePendapatan(updatePendapatanImpl: UpdatePendapatanImpl): UpdatePendapatan

  @Binds
  @Singleton
  fun bindDeleteTransfer(deleteTransferImpl: DeleteTransferImpl): DeleteTransfer
}