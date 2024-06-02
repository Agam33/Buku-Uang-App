package com.ra.bkuang.features.backuprestore.presentation

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseFragment
import com.ra.bkuang.common.util.Constants
import com.ra.bkuang.common.util.Extension.showShortToast
import com.ra.bkuang.core.preferences.UserSettingPref
import com.ra.bkuang.databinding.FragmentBackupRestoreBinding
import com.ra.bkuang.features.backuprestore.presentation.adapter.BackupRestoreAdapter
import com.ra.bkuang.features.backuprestore.presentation.dialog.CreateFileNameDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BackupRestoreFragment : BaseFragment<FragmentBackupRestoreBinding>(R.layout.fragment_backup_restore),
  CreateFileNameDialog.OnSaveListener{

  @Inject
  lateinit var userSettingPref: UserSettingPref

  private val backupRestoreViewModel: BackupRestoreViewModel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupRvTips()
    setupButton()
    observer()
  }

  private fun observer() {
    lifecycleScope.launch {
      backupRestoreViewModel.backupRestoreUiState.collect { uiState ->
        if(uiState.isSuccessful != null) {
          if(uiState.isSuccessful) {
            showShortToast(requireContext().resources.getString(R.string.msg_success))
          } else {
            showShortToast(requireContext().resources.getString(R.string.msg_failed))
          }
        }
      }
    }
  }

  private fun setupRvTips() {
    binding?.run {
      val tips = requireContext().resources.getStringArray(R.array.backup_and_restore_tips)
      val tipsAdapter = BackupRestoreAdapter(tips.toList())
      rvBackupRestoreTips.apply {
        adapter = tipsAdapter
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(requireContext())
      }
    }
  }

  private fun setupButton() {
    binding?.run {
      btnBackup.setOnClickListener {
        backup()
      }
      btnRestore.setOnClickListener {
        restore()
      }
      btnChooseDirectory.setOnClickListener {
        chooseDirectory()
      }
    }
  }

  private fun chooseDirectory() {
    val i = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
    startChooseDirectory.launch(i)
  }

  private val startChooseDirectory =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
    when(result.resultCode) {
      RESULT_OK -> {
        val uri = result.data?.data
        requireContext()
          .contentResolver
          .takePersistableUriPermission(
            uri ?: return@registerForActivityResult,
            Intent.FLAG_GRANT_WRITE_URI_PERMISSION
          )
        userSettingPref.saveFileBackupDirectory(uri.toString())
      }
      RESULT_CANCELED -> {}
    }
  }

  private fun backup() {
    val dir = userSettingPref.getFileBackupDirectory()

    if(dir?.isEmpty()!!) {
      Toast.makeText(requireContext(),
        requireContext().resources.getString(R.string.msg_empty_backup_file_directory),
        Toast.LENGTH_LONG
      ).show()
      return
    }

    CreateFileNameDialog.newInstance().apply {
      onSaveListener = this@BackupRestoreFragment
    }.show(childFragmentManager, CreateFileNameDialog.TAG)
  }

  private fun restore() {
    val i = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
       addCategory(Intent.CATEGORY_OPENABLE)
       type = "*/*"
    }
    chooseRestoreFile.launch(i)
  }

  private val chooseRestoreFile =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
      when(it.resultCode) {
        RESULT_OK -> {
          val uri = it.data?.data
          requireContext().contentResolver.takePersistableUriPermission(uri ?: return@registerForActivityResult, Intent.FLAG_GRANT_READ_URI_PERMISSION)
          val dbPath = requireContext().getDatabasePath(Constants.DB_NAME).parent
          backupRestoreViewModel.getLocalBackup(uri, dbPath ?: return@registerForActivityResult)
        }
        RESULT_CANCELED -> {}
      }
  }

  companion object {
    const val TAG = "BackupRestoreFragment"
  }

  override fun onSaveInput(name: String) {
    lifecycleScope.launch{
      val dir = userSettingPref.getFileBackupDirectory() ?: return@launch
      backupRestoreViewModel.createLocalBackup(name, dir)
    }
  }
}