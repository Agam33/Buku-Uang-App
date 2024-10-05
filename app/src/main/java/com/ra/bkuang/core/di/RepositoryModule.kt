package com.ra.bkuang.core.di

import com.ra.bkuang.core.data.source.local.database.data.HutangLocalDataSource
import com.ra.bkuang.core.data.source.local.database.data.HutangLocalDataSourceImpl
import com.ra.bkuang.features.account.data.AkunRepositoryImpl
import com.ra.bkuang.features.account.domain.repository.AkunRepository
import com.ra.bkuang.features.analytics.domain.repository.AnalyticsRepository
import com.ra.bkuang.features.analytics.data.AnalyticsRepositoryImpl
import com.ra.bkuang.features.backuprestore.data.BackupRestoreRepositoryImpl
import com.ra.bkuang.features.backuprestore.domain.repository.BackupRestoreRepository
import com.ra.bkuang.features.budget.data.BudgetRepositoryImpl
import com.ra.bkuang.features.budget.domain.repository.BudgetRepository
import com.ra.bkuang.features.category.data.KategoriRepositoryImpl
import com.ra.bkuang.features.category.domain.repository.KategoriRepository
import com.ra.bkuang.features.debt.data.HutangRepositoryImpl
import com.ra.bkuang.features.debt.data.PembayaranHutangRepositoryImpl
import com.ra.bkuang.features.debt.domain.repository.HutangRepository
import com.ra.bkuang.features.debt.domain.repository.PembayaranHutangRepository
import com.ra.bkuang.features.transaction.data.PendapatanRepositoryImpl
import com.ra.bkuang.features.transaction.data.PengeluaranRepositoryImpl
import com.ra.bkuang.features.transaction.data.TransferRepositoryImpl
import com.ra.bkuang.features.transaction.domain.repository.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.repository.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.repository.TransferRepository
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
  fun bindAnalyticsRepository(analyticsRepositoryImpl: AnalyticsRepositoryImpl): AnalyticsRepository

  @Binds
  @Singleton
  fun bindBackRestoreRepository(backupRestoreRepositoryImpl: BackupRestoreRepositoryImpl): BackupRestoreRepository

  @Binds
  @Singleton
  fun bindHutangRepository(
    pembayaranHutangRepositoryImpl: PembayaranHutangRepositoryImpl
  ): PembayaranHutangRepository

  @Binds
  @Singleton
  fun bindHutangLocalDataSource(
    hutangLocalDataSourceImpl: HutangLocalDataSourceImpl
  ): HutangLocalDataSource

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