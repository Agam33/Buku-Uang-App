package com.ra.budgetplan.domain.usecase.backuprestore

import com.ra.budgetplan.domain.usecase.backuprestore.impl.BackupDbImpl
import com.ra.budgetplan.util.Constants.DB_NAME
import com.ra.budgetplan.util.StatusItem
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.io.File

class BackupDbTest {

  @Test
  fun TestBackupShouldBeFailed() = runBlocking {
    val backupDb: BackupDb = BackupDbImpl()
    val actualStatus = StatusItem.FAILED

    val srcFile = File("/test/$DB_NAME")
    val destFile = File("/storage/emulated/0/Download")

    val expectedVal = backupDb.invoke(srcFile, destFile).last()

    Assert.assertEquals(expectedVal, actualStatus)
  }
}