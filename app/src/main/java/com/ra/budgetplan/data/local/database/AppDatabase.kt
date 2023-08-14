package com.ra.budgetplan.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ra.budgetplan.data.local.database.dao.*
import com.ra.budgetplan.domain.entity.*
import com.ra.budgetplan.util.DbLocalDateConverter
import com.ra.budgetplan.util.DbLocalDateTimeConverter

@Database(
  entities = [
    PendapatanEntity::class,
    PengeluaranEntity::class,
    TransferEntity::class,
    AkunEntity::class,
    HutangEntity::class,
    BudgetEntity::class,
    KategoriEntity::class,
    PembayaranHutangEntity::class,
    IconEntity::class
  ],
  version = 1,
  exportSchema = false
)
@TypeConverters(value = [DbLocalDateTimeConverter::class, DbLocalDateConverter::class])
abstract class AppDatabase: RoomDatabase() {
  abstract fun budgetDao(): BudgetDao
  abstract fun kategoriDao(): KategoriDao
  abstract fun pendapatanDao(): PendapatanDao
  abstract fun pengeluaranDao(): PengeluaranDao
  abstract fun akunDao(): AkunDao
  abstract fun hutangDao(): HutangDao
  abstract fun transferDao(): TransferDao
  abstract fun pembayaranHutangDao(): PembayaranHutangDao
  abstract fun iconDao(): IconDao
}