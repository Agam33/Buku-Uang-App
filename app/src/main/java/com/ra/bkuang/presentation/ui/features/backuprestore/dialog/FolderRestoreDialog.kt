package com.ra.bkuang.presentation.ui.features.backuprestore.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ra.bkuang.R
import com.ra.bkuang.presentation.settings.UserSettingPref
import com.ra.bkuang.presentation.viewmodel.BackupRestoreViewModel
import com.ra.bkuang.presentation.util.Constants.DB_BACKUP_FILE_NAME
import com.ra.bkuang.presentation.util.Constants.DB_NAME
import com.ra.bkuang.presentation.util.Extension.restartActivity
import com.ra.bkuang.domain.util.ResourceState
import com.ra.bkuang.presentation.ui.features.backuprestore.adapter.FolderBackupAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class FolderRestoreDialog: DialogFragment(),
  FolderBackupAdapter.OnItemClickListener {

  @Inject
  lateinit var userSettingPref: UserSettingPref

  private val viewModel: BackupRestoreViewModel by viewModels()

  private var backupFile: File? = null

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val inflater= activity?.layoutInflater
    val materialDialog = MaterialAlertDialogBuilder(requireContext())
    val view = inflater?.inflate(R.layout.list_file_backup_dialog, null)

    materialDialog.setView(view)

    view?.let {
      setupRvFolder(it)
      setupButton(it)
    }

    return materialDialog.create()
  }

  private fun setupRvFolder(v: View) {
    val dir = userSettingPref.getFileBackupDirectory()
    if(dir?.isEmpty()!!) {
      Toast.makeText(requireContext(),
        requireContext().resources.getString(R.string.msg_empty_backup_file_directory),
        Toast.LENGTH_LONG
      ).show()
      return
    }

    val rvFolderBackup = v.findViewById<RecyclerView>(R.id.rv_folder_backup)

    val backupFiles = getAllFileDirectory(File(dir))
    val folderBackupAdapter = FolderBackupAdapter(backupFiles)
    folderBackupAdapter.onItemClickListener = this@FolderRestoreDialog

    rvFolderBackup.apply {
      adapter = folderBackupAdapter
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(requireContext())
    }
  }

  private fun setupButton(v: View) {
    val btnRestore = v.findViewById<Button>(R.id.btn_restore)
    val btnCancel = v.findViewById<Button>(R.id.btn_cancel)

    btnRestore.setOnClickListener {
      restoreFile()
    }

    btnCancel.setOnClickListener {
      dismiss()
    }
  }

  private fun restoreFile() {

  }

  private fun getAllFileDirectory(src: File): List<File> {
    val resultFile = mutableListOf<File>()
    if(src.exists()) {
      val files = src.listFiles()
      if(files != null) {
        for(file in files) {
          if(file.isFile && file.name.startsWith(DB_BACKUP_FILE_NAME)) {
            resultFile.add(file)
          }
        }
      }
    }
    return resultFile
  }

  override fun onItemSelected(file: File) {
    backupFile = file
  }
}