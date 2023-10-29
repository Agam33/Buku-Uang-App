package com.ra.bkuang.di.core

import com.ra.bkuang.data.local.*
import com.ra.bkuang.data.local.datasourceimpl.*
import com.ra.bkuang.data.repositoryimpl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataSourceModule {

  @Binds
  @Singleton
  fun bindPembayaranHutangLocalDataSource(
    pembayaranHutangLocalDataSourceImpl: PembayaranHutangLocalDataSourceImpl
  ): PembayaranHutangLocalDataSource

  @Binds
  @Singleton
  fun bindHutangLocalDataSource(
    hutangLocalDataSourceImpl: HutangLocalDataSourceImpl
  ): HutangLocalDataSource


  @Binds
  @Singleton
  fun bindBudgetLocalDataSource(
    budgetRepositoryImpl: BudgetLocalDataSourceImpl
  ): BudgetLocalDataSource


  @Binds
  @Singleton
  fun bindKategoriLocalDataSource(
    kategoriRepositoryImpl: KategoriLocalDataSourceImpl
  ): KategoriLocalDataSource

  @Binds
  @Singleton
  fun bindPendapatanLocalDataSource(
    pendapatanRepositoryImpl: PendapatanLocalDataSourceImpl
  ): PendapatanLocalDataSource

  @Binds
  @Singleton
  fun bindPengeluaranLocalDataSource(
    pengeluaranRepositoryImpl: PengeluaranLocalDataSourceImpl
  ): PengeluaranLocalDataSource

  @Binds
  @Singleton
  fun bindTabunganLocalDataSource(
    tabunganRepositoryImpl: AkunLocalDataSourceImpl
  ): AkunLocalDataSource

  @Binds
  @Singleton
  fun bindTransferLocalDataSource(
    transferRepositoryImpl: TransferLocalDataSourceImpl
  ): TransferLocalDataSource


  @Binds
  @Singleton
  fun bindIconLocalDataSource(
    iconLocalDataSource: IconLocalDataSourceImpl
  ): IconLocalDataSource
}