package com.ra.budgetplan.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.budgetplan.domain.usecase.backuprestore.BackupDb
import com.ra.budgetplan.domain.usecase.backuprestore.RestoreDb
import com.ra.budgetplan.util.MainDispatcherRule
import com.ra.budgetplan.util.StatusItem
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.rules.TestRule
import java.io.File

class BackupRestoreViewModelTest {

  @Rule
  @JvmField
  var rule: TestRule = InstantTaskExecutorRule()

  private lateinit var backupRestoreViewModel: BackupRestoreViewModel
  private val backupDb: BackupDb = mock()
  private val restoreDb: RestoreDb = mock()

  @Before
  fun init() {
    backupRestoreViewModel = BackupRestoreViewModel(backupDb, restoreDb)
  }

  @Test
  fun `DoBackup, should be success`() = runTest{
    val src = File("/src")
    val dest = File("/dest")

    val actualStatusItem = StatusItem.SUCCESS

    whenever(backupDb.invoke(src, dest))
      .thenReturn(flow { emit(actualStatusItem) })

    backupRestoreViewModel.doBackup(src, dest)

    verify(backupDb).invoke(src, dest)
  }

  @Test
  fun `DoRestore, should be success`() {
    val src = File("/src")
    val dest = File("/dest")

    val actualStatusItem = StatusItem.SUCCESS

    whenever(restoreDb.invoke(src, dest))
      .thenReturn(flow { emit(actualStatusItem) })

    backupRestoreViewModel.doRestore(src, dest)

    verify(restoreDb).invoke(src, dest)
  }
}