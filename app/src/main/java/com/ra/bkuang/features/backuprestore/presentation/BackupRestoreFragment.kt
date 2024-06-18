package com.ra.bkuang.features.backuprestore.presentation

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseFragment
import com.ra.bkuang.common.util.Constants
import com.ra.bkuang.common.util.DrawerMenuToolbarListener
import com.ra.bkuang.common.util.Extension.showShortToast
import com.ra.bkuang.databinding.FragmentBackupRestoreBinding
import com.ra.bkuang.features.backuprestore.presentation.adapter.BackupRestoreAdapter
import com.ra.bkuang.features.backuprestore.presentation.dialog.CreateFileNameDialog
import com.ra.bkuang.features.backuprestore.presentation.viewmodel.BackupRestoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BackupRestoreFragment : BaseFragment<FragmentBackupRestoreBinding>(R.layout.fragment_backup_restore),
  CreateFileNameDialog.OnSaveListener{

  private val viewModel: BackupRestoreViewModel by viewModels()

  var drawerMenuToolbarListener: DrawerMenuToolbarListener? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupRvTips()
    setupButton()
    setupActionBar()
    observer()
  }

  private fun setupActionBar() {
    binding?.toolbar?.title = getString(R.string.txt_backup)

    binding?.toolbar?.setNavigationOnClickListener {
      drawerMenuToolbarListener?.onDrawerMenuClicked()
    }

    binding?.toolbar?.setOnMenuItemClickListener { menuItem ->
      true
    }
  }

  private fun observer() {
    lifecycleScope.launch {
      viewModel.uiState.collect { uiState ->

        uiState.isSuccessful?.let {
          if(it) {
            showShortToast(requireContext().resources.getString(R.string.msg_success))
          } else {
            showShortToast(requireContext().resources.getString(R.string.msg_failed))
          }
        }

        uiState.isDirExists?.let {
          if(it) {
            showDialogFileName()
          } else {
            showShortToast(getString(R.string.msg_empty_backup_file_directory))
          }
        }
      }
    }
  }

  private fun showDialogFileName() {
    CreateFileNameDialog.newInstance().apply {
      onSaveListener = this@BackupRestoreFragment
    }
      .show(childFragmentManager, CreateFileNameDialog.TAG)
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
        viewModel.checkBackupFile()
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
        viewModel.saveBackupFile(uri.toString())
      }
      RESULT_CANCELED -> {}
    }
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
          viewModel.getLocalBackup(uri, dbPath ?: return@registerForActivityResult)
        }
        RESULT_CANCELED -> {}
      }
  }

  override fun onSaveInput(name: String) {
    viewModel.createLocalBackup(name)
  }
}