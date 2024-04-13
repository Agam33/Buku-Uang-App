package com.ra.bkuang.features.transaction.di

import com.ra.bkuang.features.transaction.domain.usecase.CancelAlarmTransactionImpl
import com.ra.bkuang.features.transaction.domain.usecase.CancelTransactionAlarm
import com.ra.bkuang.features.transaction.domain.usecase.GetTotalTransactionByDate
import com.ra.bkuang.features.transaction.domain.usecase.GetTotalTransactionByDateImpl
import com.ra.bkuang.features.transaction.domain.usecase.SetTransactionAlarm
import com.ra.bkuang.features.transaction.domain.usecase.SetTransactionAlarmImpl
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.DeletePendapatanById
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.FindDetailPendapatanById
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetPendapatanByDate
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetPendapatanById
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatan
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatanByDate
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.SavePendapatan
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.UpdatePendapatan
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl.DeletePendapatanByIdImpl
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl.FindDetailPendapatanByIdImpl
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl.GetPendapatanByDateImpl
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl.GetPendapatanByIdImpl
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl.GetTotalPendapatanByDateImpl
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl.GetTotalPendapatanImpl
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl.SavePendapatanImpl
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl.UpdatePendapatanImpl
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.DeletePengeluaranById
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.FindDetailPengeluaranById
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetMonthlyPengeluaran
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetPengeluaranByDate
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetPengeluaranById
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetTotalPengeluaran
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetTotalPengeluaranByDate
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.SavePengeluaran
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.UpdatePengeluaran
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl.DeletePengeluaranByIdImpl
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl.FindDetailPengeluaranByIdImpl
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl.GetMonthlyPengeluaranImpl
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl.GetPengeluaranByDateImpl
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl.GetPengeluaranByIdImpl
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl.GetTotalPengeluaranByDateImpl
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl.GetTotalPengeluaranImpl
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl.SavePengeluaranImpl
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl.UpdatePengeluaranImpl
import com.ra.bkuang.features.transaction.domain.usecase.transfer.DeleteTransfer
import com.ra.bkuang.features.transaction.domain.usecase.transfer.DeleteTransferById
import com.ra.bkuang.features.transaction.domain.usecase.transfer.FindDetailTransferById
import com.ra.bkuang.features.transaction.domain.usecase.transfer.GetTransferByDate
import com.ra.bkuang.features.transaction.domain.usecase.transfer.GetTransferById
import com.ra.bkuang.features.transaction.domain.usecase.transfer.SaveTransfer
import com.ra.bkuang.features.transaction.domain.usecase.transfer.UpdateTransfer
import com.ra.bkuang.features.transaction.domain.usecase.transfer.impl.DeleteTransferByIdImpl
import com.ra.bkuang.features.transaction.domain.usecase.transfer.impl.DeleteTransferImpl
import com.ra.bkuang.features.transaction.domain.usecase.transfer.impl.FindDetailTransferByIdImpl
import com.ra.bkuang.features.transaction.domain.usecase.transfer.impl.GetTransferByDateImpl
import com.ra.bkuang.features.transaction.domain.usecase.transfer.impl.GetTransferByIdImpl
import com.ra.bkuang.features.transaction.domain.usecase.transfer.impl.SaveTransferImpl
import com.ra.bkuang.features.transaction.domain.usecase.transfer.impl.UpdateTransferImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


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