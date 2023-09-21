package com.ra.budgetplan.presentation.ui.backuprestore

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ra.budgetplan.R
import com.ra.budgetplan.base.BaseFragment
import com.ra.budgetplan.data.local.preferences.UserSettingPref
import com.ra.budgetplan.databinding.FragmentBackupRestoreBinding
import com.ra.budgetplan.presentation.ui.backuprestore.adapter.BackupRestoreAdapter
import com.ra.budgetplan.presentation.ui.backuprestore.dialog.FolderRestoreDialog
import com.ra.budgetplan.presentation.viewmodel.BackupRestoreViewModel
import com.ra.budgetplan.util.Constants.DB_BACKUP_FILE_NAME
import com.ra.budgetplan.util.Constants.DB_NAME
import com.ra.budgetplan.util.Constants.REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE
import com.ra.budgetplan.util.Constants.REQUIRED_STORAGE_PERMISSION
import com.ra.budgetplan.util.Constants.getUriPath
import com.ra.budgetplan.util.Extension.requestStoragePermission
import com.ra.budgetplan.util.Extension.restartActivity
import com.ra.budgetplan.util.StatusItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class BackupRestoreFragment : BaseFragment<FragmentBackupRestoreBinding>(R.layout.fragment_backup_restore) {

  @Inject
  lateinit var userSettingPref: UserSettingPref

  private val viewModel: BackupRestoreViewModel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupRvTips()
    setupButton()
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

  private val startForChooseDirectory =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
    when(result.resultCode) {
      RESULT_OK -> {
        val uri = result.data?.data
        val path = getUriPath(uri)
        userSettingPref.saveFileBackupDirectory(path ?: "")
      }
      RESULT_CANCELED -> {}
    }
  }

  private fun chooseDirectory() {
    val i = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
    startForChooseDirectory.launch(i)
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

    if(requestStoragePermission()) {
      lifecycleScope.launch {
        val srcFile = requireContext().getDatabasePath(DB_NAME)
        val destFile = File(dir, String.format(requireContext().resources.getString(R.string.txt_backup_format), DB_BACKUP_FILE_NAME, LocalDate.now()))

        viewModel.doBackup(srcFile, destFile).collect { status ->
          when(status) {
            StatusItem.LOADING -> {}
            StatusItem.SUCCESS -> {
              Toast.makeText(requireContext(),
                String.format(requireContext().resources.getString(R.string.msg_success_to), requireContext().resources.getString(R.string.txt_backup)),
                Toast.LENGTH_SHORT
              ).show()
              requireContext().restartActivity()
            }
            StatusItem.FAILED -> {
              Toast.makeText(requireContext(),
                String.format(requireContext().resources.getString(R.string.msg_failed_to), requireContext().resources.getString(R.string.txt_backup)),
                Toast.LENGTH_SHORT
              ).show()
            }
          }
        }
      }
    } else {
      Snackbar.make(binding?.root!!, requireContext().resources.getString(R.string.msg_allow_access_storage), Snackbar.LENGTH_LONG)
        .setAction(
          requireContext().resources.getString(R.string.msg_allowed)
        ) {
          acceptedStoragePermission()
        }.show()
    }
  }

  private fun restore() {
    val dir = userSettingPref.getFileBackupDirectory()
    if(dir?.isEmpty()!!) {
      Toast.makeText(requireContext(),
        requireContext().resources.getString(R.string.msg_empty_backup_file_directory),
        Toast.LENGTH_LONG
      ).show()
      return
    }

    if(requestStoragePermission()) {
      val folderRestoreDialog = FolderRestoreDialog()
      folderRestoreDialog.show(childFragmentManager, "restore-dialog")
    } else {
      Snackbar.make(binding?.root!!, requireContext().resources.getString(R.string.msg_allow_access_storage), Snackbar.LENGTH_LONG)
        .setAction(
          requireContext().resources.getString(R.string.msg_allowed)
        ) {
          acceptedStoragePermission()
        }.show()
    }
  }

  private fun acceptedStoragePermission() {
    ActivityCompat.requestPermissions(
      requireActivity(),
      REQUIRED_STORAGE_PERMISSION.toTypedArray(),
      REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE
    )
  }
}