package com.ra.budgetplan.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ra.budgetplan.data.local.database.AppDatabase
import com.ra.budgetplan.data.local.database.dao.AkunDao
import com.ra.budgetplan.domain.entity.AkunEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDateTime
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class AkunDaoTest {
  private lateinit var akunDao: AkunDao
  private lateinit var db: AppDatabase

  @Before
  fun createDb() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    akunDao = db.akunDao()
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    db.close()
  }

  @Test
  fun testSaveAkun() = runBlocking {
    val id = UUID.randomUUID()
    val akun = AkunEntity(id, "", "Cash",1_000_000,
      LocalDateTime.now(), LocalDateTime.now())

    akunDao.save(akun)

    val actualValue = akunDao.findById(id).first()
    println("uuid: ${actualValue.uuid}")
    Assert.assertEquals(actualValue.uuid, id)
  }
}