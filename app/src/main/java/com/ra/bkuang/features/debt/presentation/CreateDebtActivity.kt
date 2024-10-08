package com.ra.bkuang.features.debt.presentation

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseActivity
import com.ra.bkuang.common.util.ActionType
import com.ra.bkuang.common.util.Constants.DATE_PATTERN
import com.ra.bkuang.common.util.Constants.DATE_TIME_FORMATTER
import com.ra.bkuang.common.util.Constants.LOCALE_ID
import com.ra.bkuang.common.util.Extension.checkTimeFormat
import com.ra.bkuang.common.util.Extension.getStringResource
import com.ra.bkuang.common.util.Extension.millisToString
import com.ra.bkuang.common.util.Extension.parcelable
import com.ra.bkuang.common.util.Extension.setupActionBar
import com.ra.bkuang.common.util.Extension.showShortToast
import com.ra.bkuang.common.util.Extension.toCalendar
import com.ra.bkuang.common.util.getActionType
import com.ra.bkuang.databinding.ActivityCreateDebtBinding
import com.ra.bkuang.features.debt.data.model.HutangModel
import com.ra.bkuang.features.debt.presentation.DebtFragment.Companion.DEBT_EXTRA_ACTION
import com.ra.bkuang.features.debt.presentation.DebtFragment.Companion.DEBT_MODEL
import com.ra.bkuang.features.debt.presentation.viewmodel.DebtViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.UUID

@AndroidEntryPoint
class CreateDebtActivity : BaseActivity<ActivityCreateDebtBinding>(R.layout.activity_create_debt) {

  private val sharedViewModel: DebtViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupActionBar(binding.toolbar)

    observer()

    val actionType = intent?.getStringExtra(DEBT_EXTRA_ACTION) as String
    when(getActionType(actionType)) {
      ActionType.CREATE -> {
        val calendar = Calendar.getInstance()
        setupTimePicker(calendar)
        setupDatePicker(calendar)
      }

      ActionType.EDIT -> {
        setupEditDebt()
      }
    }
  }

  private fun observer() {
    lifecycleScope.launch {
      sharedViewModel.uiState.collect { uiState ->
        uiState.isSuccessfulCreate?.let {
          if(it) {
            showShortToast(getString(R.string.txt_successful_save))
          } else {
            showShortToast(getString(R.string.msg_failed))
          }
        }

        uiState.isSuccessfulUpdate?.let {
          if(it) {
            showShortToast(getString(R.string.txt_successful_update))
          } else {
            showShortToast(getString(R.string.msg_failed))
          }
        }
      }
    }
  }

  private fun setupEditDebt() {
    binding.run {
      val hutangModel = intent?.parcelable<HutangModel>(DEBT_MODEL)!!
      edtInputName.text = Editable.Factory.getInstance().newEditable(hutangModel.nama)
      edtLimit.text = Editable.Factory.getInstance().newEditable(hutangModel.maxCicilan.toString())
      edtNote.text = Editable.Factory.getInstance().newEditable(hutangModel.deskripsi)

      val calendar = hutangModel.jatuhTempo.toCalendar()
      setupTimePicker(calendar)
      setupDatePicker(calendar)
    }
  }

  private fun saveHutang() {
    binding.run {
      val name = edtInputName.text.toString()
      val maxDebt = edtLimit.text.toString()
      val desc = edtNote.text.toString()

      if (name.isBlank()) {
        showShortToast(getString(R.string.msg_empty))
        return
      }

      if (maxDebt.isBlank()) {
        showShortToast(getString(R.string.msg_empty))
        return
      }

      if(binding.edtLimit.isMaximumInput) {
        showShortToast(String.format(getString(R.string.msg_max_input), binding.edtLimit.maxInput))
        return@run
      }

      val timeStringBuilder = StringBuilder()
      timeStringBuilder.append(edtDate.text.trim())
      timeStringBuilder.append(" ")
      timeStringBuilder.append(btnTime.text.trim())
      val dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)
      val dueDate = LocalDateTime.parse(timeStringBuilder.toString(), dateTimeFormatter)

      when (getActionType(intent?.getStringExtra(DEBT_EXTRA_ACTION) as String)) {
        ActionType.CREATE -> {
          val hutangModel = HutangModel(
            UUID.randomUUID(),
            name,
            desc,
            0,
            edtLimit.getIntValue(),
            Int.MAX_VALUE,
            false,
            "",
            dueDate,
            LocalDateTime.now(),
            LocalDateTime.now()
          )
          sharedViewModel.createHutang(hutangModel)
        }

        ActionType.EDIT -> {
          val hutangModel = intent?.parcelable<HutangModel>(DEBT_MODEL)
          hutangModel?.let {
            it.nama = name
            it.deskripsi = desc
            it.maxCicilan = edtLimit.getIntValue()
            it.jatuhTempo = dueDate
            it.updatedAt = LocalDateTime.now()

            sharedViewModel.updateHutang(it)
          }
        }
      }
      finish()
    }
  }

  private fun setupTimePicker(calendar: Calendar) {
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currMinute = calendar.get(Calendar.MINUTE)

    val timePicker = MaterialTimePicker.Builder()
      .setTitleText(getString(R.string.msg_time_picker))
      .setHour(calendar.get(Calendar.HOUR_OF_DAY))
      .setMinute(calendar.get(Calendar.MINUTE))
      .setTimeFormat(TimeFormat.CLOCK_24H)
      .build()

    binding.btnTime.text = baseContext
      .getStringResource(
        R.string.txt_hour_and_minute,
        currentHour.checkTimeFormat(),
        currMinute.checkTimeFormat()
      )

    timePicker.addOnPositiveButtonClickListener {
      binding.btnTime.text = baseContext
        .getStringResource(
          R.string.txt_hour_and_minute,
          timePicker.hour.checkTimeFormat(),
          timePicker.minute.checkTimeFormat()
        )
    }

    binding.btnTime.setOnClickListener {
      timePicker.show(supportFragmentManager, "Time Picker")
    }
  }

  private fun setupDatePicker(calendar: Calendar) {
    binding.run {
      val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText(getString(R.string.msg_date_picker))
        .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
        .build()

      val sdf = SimpleDateFormat(DATE_PATTERN, LOCALE_ID)

      edtDate.text = sdf.millisToString(calendar.timeInMillis)

      edtDate.setOnClickListener {
        datePicker.show(supportFragmentManager, "Date Picker")
      }

      datePicker.addOnPositiveButtonClickListener {
        edtDate.text = sdf.millisToString(it)
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_done, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when(item.itemId) {
      android.R.id.home -> {
        onBackPressedDispatcher.onBackPressed()
        true
      }
      R.id.menu_done -> {
        saveHutang()
        true
      }
      else -> {
        super.onOptionsItemSelected(item)
      }
    }
  }
}