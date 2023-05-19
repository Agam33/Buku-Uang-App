package com.ra.budgetplan.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ra.budgetplan.data.local.database.AppDatabase
import com.ra.budgetplan.data.local.database.dao.CicilanDao
import com.ra.budgetplan.domain.entity.CicilanEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class CicilanDaoTest {
  private lateinit var db: AppDatabase
  private lateinit var cicilanDao: CicilanDao

  @Before
  fun createDb() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    cicilanDao = db.cicilanDao()
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    db.close()
  }

  @Test
  fun testSaveCicilan() = runBlocking {
    val id = UUID.randomUUID()
    val cicilan = CicilanEntity(
      id,
      "",
      100000,
      2,
      0,
    LocalDate.now(),
    LocalDateTime.now(),
    LocalDateTime.now())

    cicilanDao.save(cicilan)

    val actualValue = cicilanDao.findById(id).first()

    Assert.assertEquals(cicilan.uuid, actualValue.uuid)
    Assert.assertEquals(cicilan.maxCicilan, actualValue.maxCicilan)
    Assert.assertEquals(cicilan.idPengingat, actualValue.idPengingat)
  }
}