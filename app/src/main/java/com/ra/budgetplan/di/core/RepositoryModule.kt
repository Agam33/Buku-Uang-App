package com.ra.budgetplan.di.core

import com.ra.budgetplan.data.repositoryimpl.*
import com.ra.budgetplan.domain.repository.*
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
  fun bindBudgetRepository(budgetRepositoryImpl: BudgetRepositoryImpl): BudgetRepository

  @Binds
  @Singleton
  fun bindCicilanRepository(cicilanRepositoryImpl: CicilanRepositoryImpl): CicilanRepository

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

  @Binds
  @Singleton
  fun bindHutangRepository(hutangRepositoryImpl: HutangRepositoryImpl): HutangRepository
}