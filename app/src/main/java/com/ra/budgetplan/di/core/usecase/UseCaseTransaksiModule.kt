package com.ra.budgetplan.di.core.usecase

import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.SavePendapatan
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.impl.SavePendapatanImpl
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.SavePengeluaran
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.impl.SavePengeluaranImpl
import com.ra.budgetplan.domain.usecase.transaksi.transfer.DeleteTransfer
import com.ra.budgetplan.domain.usecase.transaksi.transfer.SaveTransfer
import com.ra.budgetplan.domain.usecase.transaksi.transfer.UpdateTransfer
import com.ra.budgetplan.domain.usecase.transaksi.transfer.impl.DeleteTransferImpl
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