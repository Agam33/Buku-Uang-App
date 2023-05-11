package com.ra.budgetplan.di.core

import android.content.Context
import androidx.room.Room
import com.ra.budgetplan.App
import com.ra.budgetplan.data.local.database.AppDatabase
import com.ra.budgetplan.data.local.database.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
 
  @Provides
  @Singleton
  fun provideDatabase(@ApplicationContext ctx: Context): AppDatabase =
    Room.databaseBuilder(
     ctx,
      AppDatabase::class.java,
      "budget_plan.db"
    )
      .build()

  @Provides @Singleton fun provideBudgetDao(db: AppDatabase): BudgetDao = db.budgetDao()
  @Provides @Singleton fun provideCicilanDao(db: AppDatabase): CicilanDao = db.cicilanDao()
  @Provides @Singleton fun provideKategoriDao(db: AppDatabase): KategoriDao = db.kategoriDao()
  @Provides @Singleton fun providePendapatanDao(db: AppDatabase): PendapatanDao = db.pendapatanDao()
  @Provides @Singleton fun providePengeluaranDao(db: AppDatabase): PengeluaranDao = db.pengeluaranDao()
  @Provides @Singleton fun provideTabunganDao(db: AppDatabase): AkunDao = db.akunDao()
  @Provides @Singleton fun provideTransferDao(db: AppDatabase): TransferDao = db.transferDao()
  @Provides @Singleton fun provideHutangDao(db: AppDatabase): HutangDao = db.hutangDao()
}