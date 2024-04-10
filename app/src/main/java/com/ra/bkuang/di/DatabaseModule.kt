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
import kotlinx.coroutines.CoroutineScope
import javax.inject.Provider
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DBName

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DBSeeder

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Provides
  @DBName
  fun provideDbName(): String = "bk_uang.db"

  @Provides
  @Singleton
  fun provideDatabase(
    @ApplicationContext ctx: Context,
    @DBName dbName: String,
    @DBSeeder databaseSeeder: Provider<DatabaseSeeder>,
  ): AppDatabase {

    return Room.databaseBuilder(
      ctx,
      AppDatabase::class.java,
      dbName
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

  @DBSeeder
  @Provides
  @Singleton fun provideDatabaseSeeder(
    @IoCoroutineScope ioScope: CoroutineScope,
    kategoriDao: KategoriDao
  ): DatabaseSeeder = DatabaseSeeder(kategoriDao, ioScope)

  @Provides @Singleton fun provideBudgetDao(db: AppDatabase): BudgetDao = db.budgetDao()
  @Provides @Singleton fun provideHutangDao(db: AppDatabase): HutangDao = db.hutangDao()
  @Provides @Singleton fun provideKategoriDao(db: AppDatabase): KategoriDao = db.kategoriDao()
  @Provides @Singleton fun providePendapatanDao(db: AppDatabase): PendapatanDao = db.pendapatanDao()
  @Provides @Singleton fun providePengeluaranDao(db: AppDatabase): PengeluaranDao = db.pengeluaranDao()
  @Provides @Singleton fun provideTabunganDao(db: AppDatabase): AkunDao = db.akunDao()
  @Provides @Singleton fun provideTransferDao(db: AppDatabase): TransferDao = db.transferDao()
  @Provides @Singleton fun providePembayaranHutangDao(db: AppDatabase): PembayaranHutangDao = db.pembayaranHutangDao()
}