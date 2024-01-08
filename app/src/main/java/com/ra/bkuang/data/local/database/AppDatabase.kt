package com.ra.bkuang.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ra.bkuang.data.local.entity.AkunEntity
import com.ra.bkuang.data.local.entity.BudgetEntity
import com.ra.bkuang.data.local.entity.HutangEntity
import com.ra.bkuang.data.local.entity.IconEntity
import com.ra.bkuang.data.local.entity.KategoriEntity
import com.ra.bkuang.data.local.entity.PembayaranHutangEntity
import com.ra.bkuang.data.local.entity.PendapatanEntity
import com.ra.bkuang.data.local.entity.PengeluaranEntity
import com.ra.bkuang.data.local.entity.TransferEntity
import com.ra.bkuang.data.local.database.dao.*
import com.ra.bkuang.data.util.DbLocalDateConverter
import com.ra.bkuang.data.util.DbLocalDateTimeConverter

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