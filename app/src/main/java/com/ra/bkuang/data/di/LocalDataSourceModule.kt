package com.ra.bkuang.data.di

import com.ra.bkuang.data.local.*
import com.ra.bkuang.data.local.datasource.*
import com.ra.bkuang.data.local.datasource.impl.AkunLocalDataSourceImpl
import com.ra.bkuang.data.local.datasource.impl.BudgetLocalDataSourceImpl
import com.ra.bkuang.data.local.datasource.impl.HutangLocalDataSourceImpl
import com.ra.bkuang.data.local.datasource.impl.KategoriLocalDataSourceImpl
import com.ra.bkuang.data.local.datasource.impl.PembayaranHutangLocalDataSourceImpl
import com.ra.bkuang.data.local.datasource.impl.PendapatanLocalDataSourceImpl
import com.ra.bkuang.data.local.datasource.impl.PengeluaranLocalDataSourceImpl
import com.ra.bkuang.data.local.datasource.impl.TransferLocalDataSourceImpl
import com.ra.bkuang.data.repository.*
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
}