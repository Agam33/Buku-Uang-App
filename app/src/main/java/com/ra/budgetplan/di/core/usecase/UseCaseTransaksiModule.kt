package com.ra.budgetplan.di.core.usecase

import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.GetPendapatanByDate
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.GetTotalPendapatan
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.SavePendapatan
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.impl.GetPendapatanByDateImpl
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.impl.GetTotalPendapatanImpl
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.impl.SavePendapatanImpl
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.GetMonthlyPengeluaran
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.GetPengeluaranByDate
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.GetTotalPengeluaran
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.SavePengeluaran
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.impl.GetMonthlyPengeluaranImpl
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.impl.GetPengeluaranByDateImpl
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.impl.GetTotalPengeluaranImpl
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.impl.SavePengeluaranImpl
import com.ra.budgetplan.domain.usecase.transaksi.transfer.DeleteTransfer
import com.ra.budgetplan.domain.usecase.transaksi.transfer.GetTransferByDate
import com.ra.budgetplan.domain.usecase.transaksi.transfer.SaveTransfer
import com.ra.budgetplan.domain.usecase.transaksi.transfer.UpdateTransfer
import com.ra.budgetplan.domain.usecase.transaksi.transfer.impl.DeleteTransferImpl
import com.ra.budgetplan.domain.usecase.transaksi.transfer.impl.GetTransferByDateImpl
import com.ra.budgetplan.domain.usecase.transaksi.transfer.impl.SaveTransferImpl
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
  fun bindUpdateTransfer(updateTransferImpl: UpdateTransfer): UpdateTransfer

  @Binds
  @Singleton
  fun bindDeleteTransfer(deleteTransferImpl: DeleteTransferImpl): DeleteTransfer
}