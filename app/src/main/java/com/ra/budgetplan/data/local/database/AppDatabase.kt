package com.ra.budgetplan.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ra.budgetplan.data.local.database.dao.*
import com.ra.budgetplan.domain.entity.*
import com.ra.budgetplan.util.DbLocalDateTimeConverter

@Database(
  entities = [
    PendapatanEntity::class,
    PengeluaranEntity::class,
    TransferEntity::class,
    TabunganEntity::class,
    HutangEntity::class,
    BudgetEntity::class,
    KategoriEntity::class
  ],
  version = 1
)
@TypeConverters(value = [DbLocalDateTimeConverter::class])
abstract class AppDatabase: RoomDatabase() {
  abstract fun budgetDao(): BudgetDao
  abstract fun kategoriDao(): KategoriDao
  abstract fun pendapatanDao(): PendapatanDao
  abstract fun pengeluaranDao(): PengeluaranDao
  abstract fun tabunganDao(): TabunganDao
  abstract fun hutangDao(): HutangDao
  abstract fun transferDao(): TransferDao
}