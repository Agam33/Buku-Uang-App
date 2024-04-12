package com.ra.bkuang.di

import com.ra.bkuang.alarm.TransactionAlarmManager
import com.ra.bkuang.alarm.TransactionAlarmManagerManagerImpl
import com.ra.bkuang.data.local.datasource.PendapatanLocalDataSource
import com.ra.bkuang.data.local.datasource.PengeluaranLocalDataSource
import com.ra.bkuang.data.local.datasource.TransferLocalDataSource
import com.ra.bkuang.data.local.datasource.impl.PendapatanLocalDataSourceImpl
import com.ra.bkuang.data.local.datasource.impl.PengeluaranLocalDataSourceImpl
import com.ra.bkuang.data.local.datasource.impl.TransferLocalDataSourceImpl
import com.ra.bkuang.domain.usecase.transaksi.CancelAlarmTransactionImpl
import com.ra.bkuang.domain.usecase.transaksi.CancelTransactionAlarm
import com.ra.bkuang.domain.usecase.transaksi.GetTotalTransactionByDate
import com.ra.bkuang.domain.usecase.transaksi.GetTotalTransactionByDateImpl
import com.ra.bkuang.domain.usecase.transaksi.SetTransactionAlarm
import com.ra.bkuang.domain.usecase.transaksi.SetTransactionAlarmImpl
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
interface TransactionModule {

  @Binds
  @Singleton
  fun bindAlarmTransactionManager(transactionAlarmManagerImpl: TransactionAlarmManagerManagerImpl): TransactionAlarmManager

  @Binds
  @Singleton
  fun bindTransferLocalDataSource(
    transferRepositoryImpl: TransferLocalDataSourceImpl
  ): TransferLocalDataSource

  @Binds
  @Singleton
  fun bindPengeluaranLocalDataSource(
    pengeluaranRepositoryImpl: PengeluaranLocalDataSourceImpl
  ): PengeluaranLocalDataSource

  @Binds
  @Singleton
  fun bindPendapatanLocalDataSource(
    pendapatanRepositoryImpl: PendapatanLocalDataSourceImpl
  ): PendapatanLocalDataSource
}


@Module
@InstallIn(SingletonComponent::class)
interface TransactionUseCaseModule {

  @Binds
  @Singleton
  fun bindCancelTransactionAlarm(cancelAlarmTransactionImpl: CancelAlarmTransactionImpl): CancelTransactionAlarm

  @Binds
  @Singleton
  fun bindSetTransactionAlarm(setTransactionAlarmImpl: SetTransactionAlarmImpl): SetTransactionAlarm

  @Binds
  @Singleton
  fun bindGetPendapatanById(getPendapatanByIdImpl: GetPendapatanByIdImpl): GetPendapatanById

  @Binds
  fun bindGetPengeluyaranById(getPengeluaranByIdImpl: GetPengeluaranByIdImpl): GetPengeluaranById

  @Binds
  fun bindGetTransferById(getTransferByIdImpl: GetTransferByIdImpl): GetTransferById

  @Binds
  fun bindDeletePendapataById(deletePendapatanByIdImpl: DeletePendapatanByIdImpl): DeletePendapatanById

  @Binds
  fun bindDeletePengeluaranById(deletePengeluaranByIdImpl: DeletePengeluaranByIdImpl): DeletePengeluaranById

  @Binds
  fun bindDeleteTransferById(deleteTransferByIdImpl: DeleteTransferByIdImpl): DeleteTransferById

  @Binds
  fun bindFindPengeluaranById(findPengeluaranByIdImpl: FindDetailPengeluaranByIdImpl): FindDetailPengeluaranById

  @Binds
  fun bindFindPendapatanById(findPendapatanByIdImpl: FindDetailPendapatanByIdImpl): FindDetailPendapatanById

  @Binds
  fun bindFindTransferById(findTransferByIdImpl: FindDetailTransferByIdImpl): FindDetailTransferById

  @Binds
  fun bindGetTotalTransactionByDate(getTotalTransactionByDateImpl: GetTotalTransactionByDateImpl): GetTotalTransactionByDate

  @Binds
  fun bindGetTotaPendapatanByDate(getTotalPendapatanByDateImpl: GetTotalPendapatanByDateImpl): GetTotalPendapatanByDate

  @Binds
  fun bindGetTotalPengeluaranByDate(getTotalPengeluaranByDateImpl: GetTotalPengeluaranByDateImpl): GetTotalPengeluaranByDate

  @Binds
  fun bindGetTotalPendapatan(getTotalPendapatanImpl: GetTotalPendapatanImpl): GetTotalPendapatan

  @Binds
  fun bindGetTotalPengeluaran(getTotalPengeluaranImpl: GetTotalPengeluaranImpl): GetTotalPengeluaran

  @Binds
  fun bindGetMonthlyPengeluaran(getMonthlyPengeluaranImpl: GetMonthlyPengeluaranImpl): GetMonthlyPengeluaran

  @Binds
  fun bindGetPengeluaranByDate(getPengeluaranByDateImpl: GetPengeluaranByDateImpl): GetPengeluaranByDate

  @Binds
  fun bindGetPendapatanByDate(getPendapatanByDateImpl: GetPendapatanByDateImpl): GetPendapatanByDate

  @Binds
  fun bindGetTransferByDate(getTransferImpl: GetTransferByDateImpl): GetTransferByDate

  @Binds
  fun bindSavePengeluaran(pengeluaranImpl: SavePengeluaranImpl): SavePengeluaran

  @Binds
  fun bindSavePendapatan(pendapatanImpl: SavePendapatanImpl): SavePendapatan

  @Binds
  fun bindSaveTransfer(saveTransferImpl: SaveTransferImpl): SaveTransfer

  @Binds
  fun bindUpdateTransfer(updateTransferImpl: UpdateTransferImpl): UpdateTransfer

  @Binds
  fun bindUpdatePengeluaran(updatePengeluaranImpl: UpdatePengeluaranImpl): UpdatePengeluaran

  @Binds
  fun bindUpdatePendapatan(updatePendapatanImpl: UpdatePendapatanImpl): UpdatePendapatan

  @Binds
  fun bindDeleteTransfer(deleteTransferImpl: DeleteTransferImpl): DeleteTransfer
}