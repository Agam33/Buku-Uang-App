package com.ra.budgetplan.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ra.budgetplan.data.local.database.AppDatabase
import com.ra.budgetplan.data.local.database.dao.CicilanDao
import com.ra.budgetplan.data.local.database.dao.KategoriDao
import com.ra.budgetplan.domain.entity.KategoriEntity
import com.ra.budgetplan.domain.entity.TipeKategori
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
class KategoriDaoTest {

  private lateinit var db: AppDatabase
  private lateinit var kategoriDao: KategoriDao

  @Before
  fun createDb() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    kategoriDao = db.kategoriDao()
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    db.close()
  }

  @Test
  fun testSaveKategori() = runBlocking {
    val id = UUID.randomUUID()
    val kategori = KategoriEntity(
      id,
      "",
      "Hello",
      TipeKategori.PENGELUARAN,
      LocalDateTime.now(),
      LocalDateTime.now()
    )

    kategoriDao.save(kategori)

    val actualValue = kategoriDao.findById(id).first()

    Assert.assertEquals(kategori.uuid, actualValue.uuid)
    Assert.assertEquals(kategori.tipeKategori, actualValue.tipeKategori)
  }
}