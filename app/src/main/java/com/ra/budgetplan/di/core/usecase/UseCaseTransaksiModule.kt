package com.ra.budgetplan.di.core.usecase

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
  fun bindSaveTransfer(saveTransferImpl: SaveTransferImpl): SaveTransfer

  @Binds
  @Singleton
  fun bindUpdateTransfer(updateTransferImpl: UpdateTransfer): UpdateTransfer

  @Binds
  @Singleton
  fun bindDeleteTransfer(deleteTransferImpl: DeleteTransferImpl): DeleteTransfer
}