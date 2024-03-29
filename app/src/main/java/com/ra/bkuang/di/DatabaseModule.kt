package com.ra.bkuang.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ra.bkuang.data.local.database.AppDatabase
import com.ra.bkuang.data.local.database.DatabaseSeeder
import com.ra.bkuang.data.local.database.dao.*
import com.ra.bkuang.util.Constants.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
 
  @Provides
  @Singleton
  fun provideDatabase(
    @ApplicationContext ctx: Context,
    databaseSeeder: Provider<DatabaseSeeder>
  ): AppDatabase {

    return Room.databaseBuilder(
      ctx,
      AppDatabase::class.java,
      DB_NAME
    )
      .allowMainThreadQueries()
      .addCallback(object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
          super.onCreate(db)
          databaseSeeder.get().seedDataCategory()
        }
      })
      .build()
  }

  @Provides @Singleton fun provideBudgetDao(db: AppDatabase): BudgetDao = db.budgetDao()
  @Provides @Singleton fun provideHutangDao(db: AppDatabase): HutangDao = db.hutangDao()
  @Provides @Singleton fun provideKategoriDao(db: AppDatabase): KategoriDao = db.kategoriDao()
  @Provides @Singleton fun providePendapatanDao(db: AppDatabase): PendapatanDao = db.pendapatanDao()
  @Provides @Singleton fun providePengeluaranDao(db: AppDatabase): PengeluaranDao = db.pengeluaranDao()
  @Provides @Singleton fun provideTabunganDao(db: AppDatabase): AkunDao = db.akunDao()
  @Provides @Singleton fun provideTransferDao(db: AppDatabase): TransferDao = db.transferDao()
  @Provides @Singleton fun providePembayaranHutangDao(db: AppDatabase): PembayaranHutangDao = db.pembayaranHutangDao()

  @Provides
  fun provideDatabaseSeeder(
    kategoriDao: KategoriDao,
  ): DatabaseSeeder =
    DatabaseSeeder(kategoriDao)
}