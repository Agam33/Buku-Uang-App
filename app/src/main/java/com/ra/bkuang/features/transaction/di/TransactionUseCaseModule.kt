package com.ra.bkuang.features.transaction.di

import com.ra.bkuang.features.transaction.domain.usecase.CancelUseCaseAlarmTransactionImpl
import com.ra.bkuang.features.transaction.domain.usecase.CancelTransactionAlarmUseCase
import com.ra.bkuang.features.transaction.domain.usecase.GetTotalTransactionByDateUseCase
import com.ra.bkuang.features.transaction.domain.usecase.GetTotalTransactionByDateUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.SetTransactionAlarmUseCase
import com.ra.bkuang.features.transaction.domain.usecase.SetTransactionAlarmUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.DeletePendapatanByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.FindDetailPendapatanByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetListDetailPendapatanByDateUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetPendapatanByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatanByDateUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatanWithFlowUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatanByDateWithFlowUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.SavePendapatanUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.UpdatePendapatanUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl.DeletePendapatanByIdUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl.FindDetailPendapatanByIdUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl.GetListDetailPendapatanByDateUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl.GetPendapatanByIdUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl.GetTotalPendapatanByDateUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl.GetTotalPendapatanByDateWithFlowUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl.GetTotalPendapatanWithFlowUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl.SavePendapatanUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl.UpdatePendapatanUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.DeletePengeluaranByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.FindDetailPengeluaranByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetMonthlyPengeluaranUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetListDetailPengeluaranByDateUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetPengeluaranByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetTotalPengeluaranWithFlowUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetTotalPengeluaranByDateUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetTotalPengeluaranByDateWithFlowUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.SavePengeluaranUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.UpdatePengeluaranUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl.DeletePengeluaranByIdUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl.FindDetailPengeluaranByIdUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl.GetMonthlyPengeluaranUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl.GetListDetailPengeluaranByDateUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl.GetPengeluaranByIdUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl.GetTotalPengeluaranByDateUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl.GetTotalPengeluaranByDateWithFlowUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl.GetTotalPengeluaranWithFlowUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl.SavePengeluaranUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl.UpdatePengeluaranUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.transfer.DeleteTransferUseCase
import com.ra.bkuang.features.transaction.domain.usecase.transfer.DeleteTransferByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.transfer.FindDetailTransferByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.transfer.GetTransferByDateUseCase
import com.ra.bkuang.features.transaction.domain.usecase.transfer.GetTransferByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.transfer.SaveTransferUseCase
import com.ra.bkuang.features.transaction.domain.usecase.transfer.UpdateTransferUseCase
import com.ra.bkuang.features.transaction.domain.usecase.transfer.impl.DeleteTransferByIdUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.transfer.impl.DeleteTransferUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.transfer.impl.FindDetailTransferByIdUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.transfer.impl.GetTransferByDateUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.transfer.impl.GetTransferByIdUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.transfer.impl.SaveTransferUseCaseImpl
import com.ra.bkuang.features.transaction.domain.usecase.transfer.impl.UpdateTransferUseCaseImpl
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
  fun bindGetTotalPengeluaranByDate(getTotalPengeluaranByDateImpl: GetTotalPengeluaranByDateUseCaseImpl): GetTotalPengeluaranByDateUseCase

  @Binds
  @Singleton
  fun bindGetTotalPendapatanByDate(getTotalPendapatanByDateImpl: GetTotalPendapatanByDateUseCaseImpl): GetTotalPendapatanByDateUseCase

  @Binds
  @Singleton
  fun bindCancelTransactionAlarm(cancelAlarmTransactionImpl: CancelUseCaseAlarmTransactionImpl): CancelTransactionAlarmUseCase

  @Binds
  @Singleton
  fun bindSetTransactionAlarm(setTransactionAlarmImpl: SetTransactionAlarmUseCaseImpl): SetTransactionAlarmUseCase

  @Binds
  @Singleton
  fun bindGetPendapatanById(getPendapatanByIdImpl: GetPendapatanByIdUseCaseImpl): GetPendapatanByIdUseCase

  @Binds
  @Singleton
  fun bindGetPengeluyaranById(getPengeluaranByIdImpl: GetPengeluaranByIdUseCaseImpl): GetPengeluaranByIdUseCase

  @Binds
  @Singleton
  fun bindGetTransferById(getTransferByIdImpl: GetTransferByIdUseCaseImpl): GetTransferByIdUseCase

  @Binds
  @Singleton
  fun bindDeletePendapataById(deletePendapatanByIdImpl: DeletePendapatanByIdUseCaseImpl): DeletePendapatanByIdUseCase

  @Binds
  @Singleton
  fun bindDeletePengeluaranById(deletePengeluaranByIdImpl: DeletePengeluaranByIdUseCaseImpl): DeletePengeluaranByIdUseCase

  @Binds
  @Singleton
  fun bindDeleteTransferById(deleteTransferByIdImpl: DeleteTransferByIdUseCaseImpl): DeleteTransferByIdUseCase

  @Binds
  @Singleton
  fun bindFindPengeluaranById(findPengeluaranByIdImpl: FindDetailPengeluaranByIdUseCaseImpl): FindDetailPengeluaranByIdUseCase

  @Binds
  @Singleton
  fun bindFindPendapatanById(findPendapatanByIdImpl: FindDetailPendapatanByIdUseCaseImpl): FindDetailPendapatanByIdUseCase

  @Binds
  @Singleton
  fun bindFindTransferById(findTransferByIdImpl: FindDetailTransferByIdUseCaseImpl): FindDetailTransferByIdUseCase

  @Binds
  @Singleton
  fun bindGetTotalTransactionByDate(getTotalTransactionByDateImpl: GetTotalTransactionByDateUseCaseImpl): GetTotalTransactionByDateUseCase

  @Binds
  @Singleton
  fun bindGetTotaPendapatanByDate(getTotalPendapatanByDateImpl: GetTotalPendapatanByDateWithFlowUseCaseImpl): GetTotalPendapatanByDateWithFlowUseCase

  @Binds
  @Singleton
  fun bindGetTotalPengeluaranByDateWithFlow(getTotalPengeluaranByDateImpl: GetTotalPengeluaranByDateWithFlowUseCaseImpl): GetTotalPengeluaranByDateWithFlowUseCase

  @Binds
  @Singleton
  fun bindGetTotalPendapatan(getTotalPendapatanImpl: GetTotalPendapatanWithFlowUseCaseImpl): GetTotalPendapatanWithFlowUseCase

  @Binds
  @Singleton
  fun bindGetTotalPengeluaranWithFlow(getTotalPengeluaranImpl: GetTotalPengeluaranWithFlowUseCaseImpl): GetTotalPengeluaranWithFlowUseCase

  @Binds
  @Singleton
  fun bindGetMonthlyPengeluaran(getMonthlyPengeluaranImpl: GetMonthlyPengeluaranUseCaseImpl): GetMonthlyPengeluaranUseCase

  @Binds
  @Singleton
  fun bindGetPengeluaranByDate(getPengeluaranByDateImpl: GetListDetailPengeluaranByDateUseCaseImpl): GetListDetailPengeluaranByDateUseCase

  @Binds
  @Singleton
  fun bindGetPendapatanByDate(getPendapatanByDateImpl: GetListDetailPendapatanByDateUseCaseImpl): GetListDetailPendapatanByDateUseCase

  @Binds
  @Singleton
  fun bindGetTransferByDate(getTransferImpl: GetTransferByDateUseCaseImpl): GetTransferByDateUseCase

  @Binds
  @Singleton
  fun bindSavePengeluaran(pengeluaranImpl: SavePengeluaranUseCaseImpl): SavePengeluaranUseCase

  @Binds
  @Singleton
  fun bindSavePendapatan(pendapatanImpl: SavePendapatanUseCaseImpl): SavePendapatanUseCase

  @Binds
  @Singleton
  fun bindSaveTransfer(saveTransferImpl: SaveTransferUseCaseImpl): SaveTransferUseCase

  @Binds
  @Singleton
  fun bindUpdateTransfer(updateTransferImpl: UpdateTransferUseCaseImpl): UpdateTransferUseCase

  @Binds
  @Singleton
  fun bindUpdatePengeluaran(updatePengeluaranImpl: UpdatePengeluaranUseCaseImpl): UpdatePengeluaranUseCase

  @Binds
  @Singleton
  fun bindUpdatePendapatan(updatePendapatanImpl: UpdatePendapatanUseCaseImpl): UpdatePendapatanUseCase

  @Binds
  @Singleton
  fun bindDeleteTransfer(deleteTransferImpl: DeleteTransferUseCaseImpl): DeleteTransferUseCase
}