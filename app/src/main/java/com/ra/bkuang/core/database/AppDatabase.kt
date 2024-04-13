package com.ra.bkuang.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ra.bkuang.core.database.converter.DbLocalDateConverter
import com.ra.bkuang.core.database.converter.DbLocalDateTimeConverter
import com.ra.bkuang.core.database.dao.AkunDao
import com.ra.bkuang.core.database.dao.BudgetDao
import com.ra.bkuang.core.database.dao.HutangDao
import com.ra.bkuang.core.database.dao.KategoriDao
import com.ra.bkuang.core.database.dao.PembayaranHutangDao
import com.ra.bkuang.core.database.dao.PendapatanDao
import com.ra.bkuang.core.database.dao.PengeluaranDao
import com.ra.bkuang.core.database.dao.TransferDao
import com.ra.bkuang.features.account.data.entity.AkunEntity
import com.ra.bkuang.features.budget.data.local.BudgetEntity
import com.ra.bkuang.features.category.data.entity.KategoriEntity
import com.ra.bkuang.features.debt.data.entity.HutangEntity
import com.ra.bkuang.features.debt.data.entity.PembayaranHutangEntity
import com.ra.bkuang.features.transaction.data.entity.PendapatanEntity
import com.ra.bkuang.features.transaction.data.entity.PengeluaranEntity
import com.ra.bkuang.features.transaction.data.entity.TransferEntity

@Database(
  entities = [
    PendapatanEntity::class,
    PengeluaranEntity::class,
    TransferEntity::class,
    AkunEntity::class,
    HutangEntity::class,
    BudgetEntity::class,
    KategoriEntity::class,
    PembayaranHutangEntity::class
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
}