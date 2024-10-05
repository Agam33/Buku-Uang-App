package com.ra.bkuang.core.di

import com.ra.bkuang.core.data.source.local.database.data.AkunLocalDataSourceImpl
import com.ra.bkuang.core.data.source.local.database.data.BudgetLocalDataSourceImpl
import com.ra.bkuang.core.data.source.local.database.data.KategoriLocalDataSourceImpl
import com.ra.bkuang.core.data.source.local.database.data.PembayaranHutangLocalDataSourceImpl
import com.ra.bkuang.features.transaction.data.local.PendapatanLocalDataSourceImpl
import com.ra.bkuang.features.transaction.data.local.PengeluaranLocalDataSourceImpl
import com.ra.bkuang.features.transaction.data.local.TransferLocalDataSourceImpl
import com.ra.bkuang.core.data.source.local.database.data.AkunLocalDataSource
import com.ra.bkuang.core.data.source.local.database.data.BudgetLocalDataSource
import com.ra.bkuang.features.transaction.data.local.PendapatanLocalDataSource
import com.ra.bkuang.features.transaction.data.local.PengeluaranLocalDataSource
import com.ra.bkuang.features.transaction.data.local.TransferLocalDataSource
import com.ra.bkuang.core.data.source.local.database.data.KategoriLocalDataSource
import com.ra.bkuang.core.data.source.local.database.data.PembayaranHutangLocalDataSource
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

  @Binds
  @Singleton
  fun bindPembayaranHutangLocalDataSource(
    pembayaranHutangLocalDataSourceImpl: PembayaranHutangLocalDataSourceImpl
  ): PembayaranHutangLocalDataSource

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
  fun bindTabunganLocalDataSource(
    tabunganRepositoryImpl: AkunLocalDataSourceImpl
  ): AkunLocalDataSource
}