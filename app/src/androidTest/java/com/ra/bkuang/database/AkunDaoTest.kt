package com.ra.bkuang.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.ra.bkuang.data.local.database.AppDatabase
import com.ra.bkuang.data.local.database.dao.AkunDao
import com.ra.bkuang.data.local.entity.AkunEntity
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
@SmallTest
class AkunDaoTest {

  private lateinit var akunDao: AkunDao
  private lateinit var database: AppDatabase

  @Before
  fun init() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    database = Room.inMemoryDatabaseBuilder(
      context, AppDatabase::class.java
    ).build()

    akunDao = database.akunDao()
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    database.close()
  }

  @Test
  fun testAkunDao() = runBlocking {
    val id = UUID.randomUUID()
    val akun = AkunEntity(
      id,
      "",
      -1,
      "CASH-1",
      100_000,
      LocalDateTime.now(),
      LocalDateTime.now()
    )

    akunDao.save(akun)


    val expectedVal1 = akunDao.findById(id)

    Assert.assertEquals(expectedVal1.uuid, id)

    akunDao.update(akun)

    akunDao.delete(akun)
  }
}