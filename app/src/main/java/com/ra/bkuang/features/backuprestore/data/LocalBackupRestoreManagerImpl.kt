package com.ra.bkuang.features.backuprestore.data

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import com.ra.bkuang.common.util.Constants
import com.ra.bkuang.common.util.Extension.isNotExist
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

class LocalBackupRestoreManagerImpl(
  private val context: Context,
): LocalBackRestoreManager {

  override suspend fun createLocalBackup(fileName: String, destDirectory: String): Boolean {
    val dbPath = context.getDatabasePath(Constants.DB_NAME)
    val dbShm = File(dbPath.parent, Constants.DB_NAME_SHM)
    val dbWal = File(dbPath.parent, Constants.DB_NAME_WAL)

    if(dbPath.isNotExist() || dbShm.isNotExist() || dbWal.isNotExist()) return false

    val dbFiles: List<File> = listOf(dbPath, dbShm, dbWal)

    val uriParse = Uri.parse(destDirectory)
    val docId = DocumentsContract.getTreeDocumentId(uriParse)
    val dirUri = DocumentsContract.buildDocumentUriUsingTree(uriParse, docId)
    val createDoc = DocumentsContract.createDocument(context.contentResolver, dirUri, "*/*", fileName)

    zipFiles(dbFiles, createDoc ?: return false)

    return true
  }

  @Throws(IOException::class)
  override suspend fun getLocalBackup(uri: Uri, directory: String) {
    unZipFile(uri, File(directory))
  }

  /**
   * @param files The files want to zip
   * @param dest Save to specific directory
   *
   * @throws IOException
   */
  @Throws(IOException::class)
  private fun zipFiles(files: List<File>, dest: Uri) {
    val buffer = ByteArray(1024)
    ZipOutputStream(context.contentResolver.openOutputStream(dest)).use { zipOutput ->
      for(file in files) {
        val fileInputStream = FileInputStream(file)
        val entry = ZipEntry(file.name)
        zipOutput.putNextEntry(entry)

        var length: Int
        while(fileInputStream.read(buffer).also { length = it } > 0) {
          zipOutput.write(buffer, 0, length)
        }

        fileInputStream.close()
        zipOutput.closeEntry()
      }
    }
  }

  /**
   * @param zipFile The file want to unzip
   * @param dest extract the zip file to specific directory
   *
   * @throws IOException
   */
  @Throws(IOException::class)
  private fun unZipFile(zipFile: Uri, dest: File) {
    val buffer = ByteArray(1024)
    ZipInputStream(context.contentResolver.openInputStream(zipFile)).use { zipInput ->
      var zipEntry: ZipEntry? = zipInput.nextEntry
      while(zipEntry != null) {
        val entryName = zipEntry.name
        val outFile = File(dest, entryName)
        val outputStream = FileOutputStream(outFile)

        var length: Int
        while (zipInput.read(buffer).also { length = it } > 0) {
          outputStream.write(buffer, 0, length)
        }

        outputStream.close()
        zipInput.closeEntry()
        zipEntry = zipInput.nextEntry
      }
    }
  }
}