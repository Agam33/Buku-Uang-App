package com.ra.budgetplan.di.core

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ra.budgetplan.App
import com.ra.budgetplan.R
import com.ra.budgetplan.data.local.database.AppDatabase
import com.ra.budgetplan.data.local.database.DatabaseSeeder
import com.ra.budgetplan.data.local.database.dao.*
import com.ra.budgetplan.domain.entity.KategoriEntity
import com.ra.budgetplan.domain.entity.TipeKategori
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
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
      "budget_plan.db"
    )
      .addCallback(object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
          super.onCreate(db)
          databaseSeeder.get().seedDataCategory()
          databaseSeeder.get().seedDataIcon()
        }
      })
      .build()
  }


  //  start provide Dao
  @Provides @Singleton fun provideBudgetDao(db: AppDatabase): BudgetDao = db.budgetDao()
  @Provides @Singleton fun provideCicilanDao(db: AppDatabase): CicilanDao = db.cicilanDao()
  @Provides @Singleton fun provideKategoriDao(db: AppDatabase): KategoriDao = db.kategoriDao()
  @Provides @Singleton fun providePendapatanDao(db: AppDatabase): PendapatanDao = db.pendapatanDao()
  @Provides @Singleton fun providePengeluaranDao(db: AppDatabase): PengeluaranDao = db.pengeluaranDao()
  @Provides @Singleton fun provideTabunganDao(db: AppDatabase): AkunDao = db.akunDao()
  @Provides @Singleton fun provideTransferDao(db: AppDatabase): TransferDao = db.transferDao()
  @Provides @Singleton fun provideHutangDao(db: AppDatabase): HutangDao = db.hutangDao()
  @Provides @Singleton fun provideIconDao(db: AppDatabase): IconDao = db.iconDao()

  //  end provide Dao

  @Provides
  fun provideDatabaseSeeder(
    @ApplicationContext context: Context,
    kategoriDao: KategoriDao,
    iconDao: IconDao
  ): DatabaseSeeder =
    DatabaseSeeder(context, kategoriDao, iconDao)
}