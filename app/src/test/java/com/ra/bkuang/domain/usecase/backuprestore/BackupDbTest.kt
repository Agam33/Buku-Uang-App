package com.ra.bkuang.domain.usecase.backuprestore

import com.ra.bkuang.domain.usecase.backuprestore.impl.BackupDbImpl
import com.ra.bkuang.util.Constants.DB_NAME
import com.ra.bkuang.util.ResourceState
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.io.File

class BackupDbTest {

  @Test
  fun TestBackupShouldBeFailed() = runBlocking {
    val backupDb: BackupDb = BackupDbImpl()
    val actualStatus = ResourceState.FAILED

    val srcFile = File("/test/$DB_NAME")
    val destFile = File("/storage/emulated/0/Download")

    val expectedVal = backupDb.invoke(srcFile, destFile).last()

    Assert.assertEquals(expectedVal, actualStatus)
  }
}