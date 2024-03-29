package com.ra.bkuang.presentation.ui.backuprestore

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.bkuang.R
import com.ra.bkuang.databinding.FragmentBackupRestoreBinding
import com.ra.bkuang.data.preferences.UserSettingPref
import com.ra.bkuang.presentation.base.BaseFragment
import com.ra.bkuang.presentation.ui.backuprestore.adapter.BackupRestoreAdapter
import com.ra.bkuang.presentation.ui.backuprestore.dialog.CreateFileNameDialog
import com.ra.bkuang.util.Constants
import com.ra.bkuang.util.Extension.isNotExist
import com.ra.bkuang.util.Extension.showShortToast
import com.ra.bkuang.util.FileUtils
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class BackupRestoreFragment : BaseFragment<FragmentBackupRestoreBinding>(R.layout.fragment_backup_restore) {

  @Inject
  lateinit var userSettingPref: UserSettingPref

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

  private fun chooseDirectory() {
    val i = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
    startChooseDirectory.launch(i)
  }

  private val startChooseDirectory =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
    when(result.resultCode) {
      RESULT_OK -> {
        val uri = result.data?.data
        requireContext().contentResolver.takePersistableUriPermission(uri ?: return@registerForActivityResult, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
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

    val dbPath = requireContext().getDatabasePath(Constants.DB_NAME)
    val dbShm = File(dbPath.parent, Constants.DB_NAME_SHM)
    val dbWal = File(dbPath.parent, Constants.DB_NAME_WAL)

    if(dbPath.isNotExist() || dbShm.isNotExist() || dbWal.isNotExist()) return

    val dbFiles: List<File> = listOf(dbPath, dbShm, dbWal)

    CreateFileNameDialog.newInstance().apply {
      onSaveListener = object : CreateFileNameDialog.OnSaveListener {
        override fun onSaveInput(name: String) {
          try {
            val uriParse = Uri.parse(dir)
            val docId = DocumentsContract.getTreeDocumentId(uriParse)
            val dirUri = DocumentsContract.buildDocumentUriUsingTree(uriParse, docId)
            val createDoc = DocumentsContract.createDocument(requireContext().contentResolver, dirUri, "*/*", name)

            FileUtils.zipFiles(requireContext(), dbFiles, createDoc ?: return)

            showShortToast(requireContext().resources.getString(R.string.msg_success))
          } catch (e: Exception) {
            showShortToast(requireContext().resources.getString(R.string.msg_failed))
            Timber.tag("$TAG-backup()").d(e)
          }
        }
      }
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
          try {
            val uri = it.data?.data
            requireContext().contentResolver.takePersistableUriPermission(uri ?: return@registerForActivityResult, Intent.FLAG_GRANT_READ_URI_PERMISSION)

            val dbPath = requireContext().getDatabasePath(Constants.DB_NAME).parent

            FileUtils.unZipFile(requireContext(), uri, File(dbPath ?: return@registerForActivityResult))

            showShortToast(requireContext().resources.getString(R.string.msg_success))
          } catch (e: Exception) {
            showShortToast(requireContext().resources.getString(R.string.msg_failed))
            Timber.tag("$TAG-restore()").d(e)
          }
        }
        RESULT_CANCELED -> {}
      }
  }

  companion object {
    const val TAG = "BackupRestoreFragment"
  }
}