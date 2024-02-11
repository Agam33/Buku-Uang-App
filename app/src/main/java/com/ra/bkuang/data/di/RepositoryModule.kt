package com.ra.bkuang.data.di

import com.ra.bkuang.data.repository.*
import com.ra.bkuang.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

  @Binds
  @Singleton
  fun bindHutangRepository(pembayaranHutangRepositoryImpl: PembayaranHutangRepositoryImpl): PembayaranHutangRepository

  @Binds
  @Singleton
  fun bindBudgetRepository(budgetRepositoryImpl: BudgetRepositoryImpl): BudgetRepository

  @Binds
  @Singleton
  fun bindCicilanRepository(hutangRepositoryImpl: HutangRepositoryImpl): HutangRepository

  @Binds
  @Singleton
  fun bindKategoriRepository(kategoriRepositoryImpl: KategoriRepositoryImpl): KategoriRepository

  @Binds
  @Singleton
  fun bindPendapatanRepository(pendapatanRepositoryImpl: PendapatanRepositoryImpl): PendapatanRepository

  @Binds
  @Singleton
  fun bindPengeluaranRepository(pengeluaranRepositoryImpl: PengeluaranRepositoryImpl): PengeluaranRepository

  @Binds
  @Singleton
  fun bindTabungaRepository(akunRepositoryImpl: AkunRepositoryImpl): AkunRepository

  @Binds
  @Singleton
  fun bindTransferRepository(transferRepositoryImpl: TransferRepositoryImpl): TransferRepository
}