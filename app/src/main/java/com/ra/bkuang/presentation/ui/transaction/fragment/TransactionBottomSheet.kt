package com.ra.bkuang.presentation.ui.transaction.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.ra.bkuang.R
import com.ra.bkuang.data.preferences.UserSettingPref
import com.ra.bkuang.databinding.FragmentTransactionBottomSheetBinding
import com.ra.bkuang.util.Constants
import com.ra.bkuang.util.DateViewType
import com.ra.bkuang.util.Extension.cancelDailyWorker
import com.ra.bkuang.util.Extension.checkTimeFormat
import com.ra.bkuang.util.Extension.getStringResource
import com.ra.bkuang.util.Extension.setDailyWorker
import com.ra.bkuang.util.getDateViewType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Calendar
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class TransactionBottomSheet: BottomSheetDialogFragment() {

  @Inject
  lateinit var userSettingPref: UserSettingPref

  private var _binding: FragmentTransactionBottomSheetBinding? = null
  private val binding get() = _binding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction_bottom_sheet, container, false)
    return _binding?.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    handleTransactionView()
    handleAlarmTransaction()
  }

  private fun handleAlarmTransaction() {
    val calendar = Calendar.getInstance()

    val timePicker = MaterialTimePicker.Builder()
      .setTitleText(getString(R.string.msg_time_picker))
      .setHour(calendar.get(Calendar.HOUR_OF_DAY))
      .setMinute(calendar.get(Calendar.MINUTE))
      .setTimeFormat(TimeFormat.CLOCK_24H)
      .build()

    binding?.tvAlarmTime?.setOnClickListener {
      timePicker.show(childFragmentManager, Constants.TAG_TIME_PICKER_FRAGMENT)
    }

    lifecycleScope.launch {
      userSettingPref.getTextAlarmTransaction().collect {
        binding?.tvAlarmTime?.text = it
      }
    }

    viewLifecycleOwner.lifecycleScope.launch {
     userSettingPref.isActiveAlarmTransaction().collect {
       binding?.switchActiveAlarm?.isChecked = it
     }
    }

    timePicker.addOnPositiveButtonClickListener {
      lifecycleScope.launch {
        userSettingPref.setTextAlarmTransaction(
          requireContext()
            .getStringResource(
              R.string.txt_alarm_transaction_time,
              timePicker.hour.checkTimeFormat(),
              timePicker.minute.checkTimeFormat()
            )
        )
        delay(200)
        if(binding?.switchActiveAlarm?.isChecked!!) {
          Timber.d("Set new Alarm")
          requireContext().setDailyWorker(getAlarmCalendar(binding?.tvAlarmTime?.text?.toString() ?: "00 : 00"))
        }
      }
    }

    binding?.switchActiveAlarm?.setOnCheckedChangeListener { _, isChecked ->
      viewLifecycleOwner.lifecycleScope.launch {
        if (isChecked) {
          Timber.d("Set Alarm")
          requireContext().setDailyWorker(getAlarmCalendar(binding?.tvAlarmTime?.text?.toString() ?: "00 : 00"))
        } else {
          Timber.d("Cancel Alarm")
          requireContext().cancelDailyWorker(Constants.TRANSACTION_DAILY_WORKER_NAME)
        }
        userSettingPref.setAlarmTransaction(isChecked)
      }
    }
  }

  private fun handleTransactionView() {
    val rbDaily = binding?.rbDaily?.id ?: 0
    val rbMonthly = binding?.rbMonthly?.id ?: 0

    lifecycleScope.launch {
      val type = userSettingPref.getDateViewType().first()
      when(getDateViewType(type)) {
        DateViewType.DAILY -> binding?.radioGroup?.check(rbDaily)
        else -> binding?.radioGroup?.check(rbMonthly)
      }
    }

    binding?.radioGroup?.setOnCheckedChangeListener { _, checkedId ->
      when(checkedId) {
        rbDaily -> {
          lifecycleScope.launch {
            userSettingPref.saveDateViewType(DateViewType.DAILY)
          }
        }
        rbMonthly -> {
          lifecycleScope.launch {
            userSettingPref.saveDateViewType(DateViewType.MONTHLY)
          }
        }
      }
    }
  }

  private fun getAlarmCalendar(text: String): Calendar {
    val hhMM = text.split(' ')
    val hours = hhMM[0].toInt()
    val minutes = hhMM[2].toInt()

    val alarmCalendar = Calendar.getInstance(TimeZone.getDefault())
    alarmCalendar.set(Calendar.HOUR_OF_DAY, hours)
    alarmCalendar.set(Calendar.MINUTE, minutes)
    alarmCalendar.set(Calendar.SECOND, 0)
    alarmCalendar.set(Calendar.MILLISECOND, 0)

    val now = System.currentTimeMillis()

    if(alarmCalendar.timeInMillis <= now) {
      alarmCalendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    val interval = alarmCalendar.timeInMillis - now
    val nHour = TimeUnit.MINUTES.toHours(TimeUnit.MILLISECONDS.toMinutes(interval))
    val nMinute = TimeUnit.SECONDS.toMinutes(TimeUnit.MILLISECONDS.toSeconds(interval)) % 60

    Toast.makeText(
      requireContext(),
      "Alarm akan aktif dalam $nHour jam " +
              "$nMinute menit.",
      Toast.LENGTH_SHORT
    ).show()

    return alarmCalendar
  }

  companion object {
    const val TAG = "TransactionBottomSheet"
  }
}