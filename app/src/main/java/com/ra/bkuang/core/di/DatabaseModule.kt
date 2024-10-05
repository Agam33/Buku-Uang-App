package com.ra.bkuang.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ra.bkuang.core.data.source.local.database.AppDatabase
import com.ra.bkuang.core.data.source.local.database.DatabaseSeeder
import com.ra.bkuang.core.data.source.local.database.dao.AkunDao
import com.ra.bkuang.core.data.source.local.database.dao.BudgetDao
import com.ra.bkuang.core.data.source.local.database.dao.HutangDao
import com.ra.bkuang.core.data.source.local.database.dao.KategoriDao
import com.ra.bkuang.core.data.source.local.database.dao.PembayaranHutangDao
import com.ra.bkuang.core.data.source.local.database.dao.PendapatanDao
import com.ra.bkuang.core.data.source.local.database.dao.PengeluaranDao
import com.ra.bkuang.core.data.source.local.database.dao.TransferDao
import com.ra.bkuang.common.di.DBNameQualifier
import com.ra.bkuang.common.di.DBSeederQualifier
import com.ra.bkuang.common.di.IoCoroutineScopeQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Provides
  @DBNameQualifier
  fun provideDbName(): String = "bk_uang.db"

  @Provides
  @Singleton
  fun provideDatabase(
      @ApplicationContext ctx: Context,
      @DBNameQualifier dbNameQualifier: String,
      @DBSeederQualifier databaseSeeder: Provider<DatabaseSeeder>,
  ): AppDatabase {

    return Room.databaseBuilder(
      ctx,
      AppDatabase::class.java,
      dbNameQualifier
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

  @DBSeederQualifier
  @Provides
  @Singleton fun provideDatabaseSeeder(
    @IoCoroutineScopeQualifier ioScope: CoroutineScope,
    kategoriDao: KategoriDao
  ): DatabaseSeeder =
    DatabaseSeeder(kategoriDao, ioScope)

  @Provides
  @Singleton
  fun provideBudgetDao(db: AppDatabase): BudgetDao = db.budgetDao()
  @Provides
  @Singleton
  fun provideHutangDao(db: AppDatabase): HutangDao = db.hutangDao()
  @Provides
  @Singleton
  fun provideKategoriDao(db: AppDatabase): KategoriDao = db.kategoriDao()
  @Provides
  @Singleton
  fun providePendapatanDao(db: AppDatabase): PendapatanDao = db.pendapatanDao()
  @Provides
  @Singleton
  fun providePengeluaranDao(db: AppDatabase): PengeluaranDao = db.pengeluaranDao()
  @Provides
  @Singleton
  fun provideTabunganDao(db: AppDatabase): AkunDao = db.akunDao()
  @Provides
  @Singleton
  fun provideTransferDao(db: AppDatabase): TransferDao = db.transferDao()
  @Provides
  @Singleton
  fun providePembayaranHutangDao(db: AppDatabase): PembayaranHutangDao = db.pembayaranHutangDao()
}