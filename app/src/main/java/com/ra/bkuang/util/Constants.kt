package com.ra.bkuang.util

import android.Manifest
import android.animation.ValueAnimator
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.view.View
import android.view.animation.DecelerateInterpolator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
import java.util.Locale
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

object Constants {

  const val DB_NAME = "bk_uang.db"
  const val DB_BACKUP_FILE_NAME = "bk_uang"
  const val DAILY_DATE_FORMAT = "MMMM d, yyyy"
  const val MONTHLY_DATE_FORMAT = "MMMM, yyyy"
  const val FILE_USER_SETTING_PREF = "user_setting_pref.pb"
  const val FILE_USER_SETTING_SHARED_PREF = "user_setting_shared_pref"
  const val DATE_PATTERN = "yyyy-MM-dd"
  const val DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm"
  const val ALARM_RECEIVER_NOTIFICATION_ID = 201
  const val ALARM_RECEIVER_NOTIFICATION_CHANNEL_ID = "alarm-receiver-channel-1"
  const val ALARM_RECEIVER_NOTIFICATION_CHANNEL_NAME = "alarm-receiver"
  const val REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE = 102
  const val TAG_TIME_PICKER_FRAGMENT = "tag-time-picker-fragment"
  const val ALARM_TRANSACTION_NOTIFICATION_CHANNEL_ID = "alarm-transaction-ch-1"
  const val ALARM_TRANSACTION_NOTIFICATION_CHANNEL_NAME = "alarm-transaction"
  const val TRANSACTION_ALARM_ID = 3000001
  const val TRANSACTION_DAILY_WORKER_NAME = "transaction-daily-worker-name"

  val LOCALE_ID = Locale("id", "ID")

  val REQUIRED_STORAGE_PERMISSION = mutableListOf(
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_EXTERNAL_STORAGE
  )

  fun Int.isOverBudget(maxValue: Int): Boolean = this > maxValue

  inline fun coroutineIOThread(crossinline action: suspend () -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
      action()
    }
  }

  fun LocalDate.toMonthlyTime(): Pair<LocalDateTime, LocalDateTime> {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, this.year)
    calendar.set(Calendar.MONTH, this.month.value - 1)
    val localDateTime = LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault())

    val fromDate = localDateTime.withDayOfMonth(1)
      .withHour(0)
      .withMinute(0)
    val toDate = localDateTime.with(TemporalAdjusters.lastDayOfMonth())
      .withHour(23)
      .withMinute(59)

    return fromDate to toDate
  }

  fun calculatePercent(currValue: Int, maxValue: Long): Double =
    ((currValue * 1.0) / (maxValue * 1.0 )) * 100.0

  fun expandedWidth(v: View, duration: Long, targetWidth: Int) {
    val prevWidth = v.width

    v.visibility = View.VISIBLE
    val valueAnimator: ValueAnimator = ValueAnimator.ofInt(prevWidth, targetWidth)
    valueAnimator.addUpdateListener { animation ->
      v.layoutParams.width = animation.animatedValue as Int
      v.requestLayout()
    }

    valueAnimator.interpolator = DecelerateInterpolator()
    valueAnimator.duration = duration
    valueAnimator.start()
  }

  fun collapsedWidth(v: View, duration: Long, targetWidth: Int) {
    val prevWidth = v.width
    val valueAnimator: ValueAnimator = ValueAnimator.ofInt(prevWidth, targetWidth)
    valueAnimator.addUpdateListener { animation ->
      v.layoutParams.width = animation.animatedValue as Int
      v.requestLayout()
    }

    valueAnimator.interpolator = DecelerateInterpolator()
    valueAnimator.duration = duration
    valueAnimator.start()
  }


  /**
   * @param files The files want to zip
   * @param dest Save file to specific directory
   *
   * @throws IOException
   */
  @Throws(IOException::class)
  fun zipFiles(files: List<File>, dest: File) {
    val buffer = ByteArray(1024)
    ZipOutputStream(FileOutputStream(dest)).use { zipOutput ->
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
   * @param dest Extract the zip file to specific directory
   *
   * @throws IOException
   */
  @Throws(IOException::class)
  fun unZipFile(zipFile: File, dest: File) {
    val buffer = ByteArray(1024)

    ZipInputStream(FileInputStream(zipFile)).use { zipInput ->
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

  fun getUriPath(uri: Uri?): String? {
    if(isDownloadsDocument(uri)) {
      val id = DocumentsContract.getTreeDocumentId(uri)
      if(id == "downloads") {
        val f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        return f.absolutePath
      }
      return id.split(":")[1]
    }

    return null
  }

  private fun isDownloadsDocument(uri: Uri?): Boolean {
    return "com.android.providers.downloads.documents" == uri?.authority
  }
}