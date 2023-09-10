package com.ra.budgetplan.customview.dialog

import android.app.Dialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.ra.budgetplan.R
import com.ra.budgetplan.util.Constants.DATE_PATTERN
import com.ra.budgetplan.util.Constants.LOCALE_ID
import com.ra.budgetplan.util.Extension.checkTimeFormat
import com.ra.budgetplan.util.Extension.getStringResource
import com.ra.budgetplan.util.Extension.millisToString
import com.ra.budgetplan.util.Extension.showShortToast
import java.text.ParseException
import java.util.Calendar
import java.util.Locale

class CalendarDialog: DialogFragment() {

  interface OnSetAlarmListener {
    fun onSave(calendar: Calendar)
    fun onCancel()
  }

  private lateinit var tvDate: TextView
  private lateinit var tvTime: TextView
  private lateinit var ibDate: ImageButton
  private lateinit var ibTime: ImageButton
  private lateinit var ibClose: ImageButton
  private lateinit var ibDone: ImageButton

  var onSetAlarmListener: OnSetAlarmListener? = null

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val inflater = activity?.layoutInflater
    val materialDialog = MaterialAlertDialogBuilder(requireContext())
    val view = inflater?.inflate(R.layout.set_alarm_dialog, null)

    materialDialog.setView(view)

    view?.let {
      initView(it)
    }

    setupTimePicker()
    setupDatePicker()

    closeDialog()
    saveDateAndTime()

    return materialDialog.create()
  }

  private fun initView(v: View) {
    tvDate = v.findViewById(R.id.tv_calendar)
    tvTime = v.findViewById(R.id.tv_time)
    ibDate = v.findViewById(R.id.ib_date)
    ibTime = v.findViewById(R.id.ib_time)
    ibClose = v.findViewById(R.id.ib_close_dialog)
    ibDone = v.findViewById(R.id.ib_done)
  }

  private fun closeDialog() {
    ibClose.setOnClickListener {
      onSetAlarmListener?.onCancel()
      dismiss()
    }
  }

  private fun saveDateAndTime() {
    ibDone.setOnClickListener {

      val date = tvDate.text.toString()
      val time = tvTime.text.toString()

      if(isDateInvalid(date, DATE_PATTERN) || isDateInvalid(time, TIME_FORMAT))
        return@setOnClickListener

      if(date.isBlank() || time.isBlank() || date == "-" || time == "-") {
        showShortToast(requireContext().getString(R.string.msg_empty))
        return@setOnClickListener
      }

      val dateArray = date.split("-")
      val timeArray = time.split(":")

      val calendar = Calendar.getInstance()

      // Set Date
      calendar.set(Calendar.YEAR, dateArray[0].toInt())
      calendar.set(Calendar.MONTH, dateArray[1].toInt() - 1)
      calendar.set(Calendar.DAY_OF_MONTH, dateArray[2].toInt())

      // Set Time
      calendar.set(Calendar.HOUR_OF_DAY, timeArray[0].toInt())
      calendar.set(Calendar.MINUTE, timeArray[1].toInt())
      calendar.set(Calendar.SECOND, 0)

      onSetAlarmListener?.onSave(calendar)
    }
  }

  private fun setupTimePicker() {
    val cal = Calendar.getInstance()

    val timePicker = MaterialTimePicker.Builder()
      .setTitleText(requireContext().getString(R.string.msg_time_picker))
      .setHour(cal.get(Calendar.HOUR_OF_DAY))
      .setMinute(cal.get(Calendar.MINUTE))
      .setTimeFormat(TimeFormat.CLOCK_24H)
      .build()

    timePicker.addOnPositiveButtonClickListener {
      tvTime.text = requireContext().getStringResource(
          R.string.txt_hour_and_minute,
          timePicker.hour.checkTimeFormat(),
          timePicker.minute.checkTimeFormat()
        )
    }

    ibTime.setOnClickListener {
      timePicker.show(childFragmentManager, "Time Picker")
    }

    cal.clear()
  }

  private fun setupDatePicker() {
      val datePicker = MaterialDatePicker.Builder
        .datePicker()
        .setTitleText(getString(R.string.msg_date_picker))
        .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
        .build()

    val sdf = SimpleDateFormat(DATE_PATTERN, LOCALE_ID)

    ibDate.setOnClickListener {
      datePicker.show(childFragmentManager, "Date Picker")
    }

    datePicker.addOnPositiveButtonClickListener {
      tvDate.text = sdf.millisToString(it)
    }
  }

  private fun isDateInvalid(date: String, format: String): Boolean {
    return try {
      val df = java.text.SimpleDateFormat(format, Locale.getDefault())
      df.isLenient = false
      df.parse(date)
      false
    } catch (e: ParseException) {
      true
    }
  }

  companion object {
    private const val TIME_FORMAT = "HH:mm"
  }
}