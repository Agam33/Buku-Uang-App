package com.ra.bkuang.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.ra.bkuang.domain.util.ResourceState
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.io.File

class BackupRestoreViewModelTest {

  @Rule
  @JvmField
  var rule: TestRule = InstantTaskExecutorRule()

  private lateinit var backupRestoreViewModel: BackupRestoreViewModel

  @Before
  fun init() {
    backupRestoreViewModel = BackupRestoreViewModel()
  }

  @Test
  fun `DoBackup, should be success`() = runTest{
//    val src = File("/src")
//    val dest = File("/dest")
//
//    val actualResourceState = ResourceState.SUCCESS
//
//    whenever(backupDb.invoke(src, dest))
//      .thenReturn(flow { emit(actualResourceState) })
//
//    backupRestoreViewModel.doBackup(src, dest)
//
//    verify(backupDb).invoke(src, dest)
  }

  @Test
  fun `DoRestore, should be success`() {
//    val src = File("/src")
//    val dest = File("/dest")
//
//    val actualResourceState = ResourceState.SUCCESS
//
//    whenever(restoreDb.invoke(src, dest))
//      .thenReturn(flow { emit(actualResourceState) })
//
//    backupRestoreViewModel.doRestore(src, dest)
//
//    verify(restoreDb).invoke(src, dest)
  }
}