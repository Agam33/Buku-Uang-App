package com.ra.bkuang.common.util

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

object FileUtils {

  /**
   * @param files The files want to zip
   * @param dest Save to specific directory
   *
   * @throws IOException
   */
  @Throws(IOException::class)
  fun zipFiles(context: Context, files: List<File>, dest: Uri) {
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
  fun unZipFile(context: Context, zipFile: Uri, dest: File) {
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