package com.ra.bkuang.core.di

import com.ra.bkuang.features.account.data.local.AkunLocalDataSourceImpl
import com.ra.bkuang.features.budget.data.local.BudgetLocalDataSourceImpl
import com.ra.bkuang.features.category.data.local.KategoriLocalDataSourceImpl
import com.ra.bkuang.features.debt.data.local.PembayaranHutangLocalDataSourceImpl
import com.ra.bkuang.features.transaction.data.local.PendapatanLocalDataSourceImpl
import com.ra.bkuang.features.transaction.data.local.PengeluaranLocalDataSourceImpl
import com.ra.bkuang.features.transaction.data.local.TransferLocalDataSourceImpl
import com.ra.bkuang.features.account.data.local.AkunLocalDataSource
import com.ra.bkuang.features.budget.data.local.BudgetLocalDataSource
import com.ra.bkuang.features.transaction.data.local.PendapatanLocalDataSource
import com.ra.bkuang.features.transaction.data.local.PengeluaranLocalDataSource
import com.ra.bkuang.features.transaction.data.local.TransferLocalDataSource
import com.ra.bkuang.features.category.data.local.KategoriLocalDataSource
import com.ra.bkuang.features.debt.data.local.PembayaranHutangLocalDataSource
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