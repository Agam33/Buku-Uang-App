package com.ra.budgetplan.presentation.ui.transaction.fragment

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import com.ra.budgetplan.R
import com.ra.budgetplan.customview.spinner.TransactionSpinnerAdapter
import com.ra.budgetplan.databinding.FragmentCreateTransferBinding
import com.ra.budgetplan.domain.model.AkunModel
import com.ra.budgetplan.domain.model.TransferModel
import com.ra.budgetplan.presentation.viewmodel.TransactionViewModel
import com.ra.budgetplan.util.DATE_PATTERN
import com.ra.budgetplan.util.DATE_TIME_FORMATTER
import com.ra.budgetplan.util.checkTimeFormat
import com.ra.budgetplan.util.getStringResource
import com.ra.budgetplan.util.millisToString
import com.ra.budgetplan.util.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.UUID

@AndroidEntryPoint
class CreateTransferFragment : Fragment() {

  private var _binding: FragmentCreateTransferBinding? = null
  private val binding get() = _binding

  private val viewModel: TransactionViewModel by viewModels()

  private lateinit var fromAccountSpinnerAdapter: TransactionSpinnerAdapter<AkunModel>
  private lateinit var toAccountSpinnerAdapter: TransactionSpinnerAdapter<AkunModel>

  private val currentTime = Calendar.getInstance()
  private var currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
  private var currentMinute = currentTime.get(Calendar.MINUTE)

  private var fromAccountId: UUID? = null
  private var toAccountId: UUID? = null

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    _binding = FragmentCreateTransferBinding.inflate(inflater, container, false)
    observer()
    setupDatePicker()
    setupTimePicker()
    setupAccountPicker()
    createAccount()
    return binding?.root
  }

  private fun setupAccountPicker() {
    binding?.run {
      spToAccount.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, p3: Long) {
          val model = adapter?.getItemAtPosition(position) as AkunModel
          toAccountId = model.uuid
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
      }

      spFromAccount.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, p3: Long) {
          val model = adapter?.getItemAtPosition(position) as AkunModel
          fromAccountId = model.uuid
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
      }
    }
  }

  private fun createAccount() {
    binding?.run {
      btnSave.setOnClickListener {
        validateInput()
      }
    }
  }

  private fun validateInput() {
    binding?.run {
      val amount: String = edtAmount.text.toString()
      val note: String = edtNote.text.toString()

      if(amount.isBlank()) {
        showShortToast(getString(R.string.msg_empty))
        return
      }

      val timeStringBuilder = StringBuilder()
      timeStringBuilder.append(edtDate.text.trim())
      timeStringBuilder.append(" ")
      timeStringBuilder.append(btnTime.text.trim())
      val dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)
      val createdAt = LocalDateTime.parse(timeStringBuilder.toString(), dateTimeFormatter)

      val transferModel = TransferModel(
        uuid = UUID.randomUUID(),
        jumlah = amount.toInt(),
        createdAt = createdAt,
        updatedAt = createdAt,
        deskripsi = note,
        idFromAkun = fromAccountId ?: return@run,
        idToAkun = toAccountId ?: return@run
      )

      currentTime.clear()

      viewModel.saveTransfer(transferModel)

      showShortToast(getString(R.string.msg_success))

      activity?.finish()
    }
  }

  private fun setupTimePicker() {
    val timePicker = MaterialTimePicker.Builder()
      .setTitleText(getString(R.string.msg_time_picker))
      .setHour(currentTime.get(Calendar.HOUR_OF_DAY))
      .setMinute(currentTime.get(Calendar.MINUTE))
      .setTimeFormat(CLOCK_24H)
      .build()

    binding?.btnTime?.text = requireContext()
      .getStringResource(
        R.string.txt_hour_and_minute,
        currentHour.checkTimeFormat(),
        currentMinute.checkTimeFormat()
      )

    timePicker.addOnPositiveButtonClickListener {
      binding?.btnTime?.text = requireContext()
        .getStringResource(
          R.string.txt_hour_and_minute,
          timePicker.hour.checkTimeFormat(),
          timePicker.minute.checkTimeFormat()
        )
    }

    binding?.btnTime?.setOnClickListener {
      timePicker.show(parentFragmentManager, "Time Picker")
    }
  }

  private fun setupDatePicker() {
    binding?.run {

      val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText(getString(R.string.msg_date_picker))
        .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
        .build()

      val sdf = SimpleDateFormat(DATE_PATTERN, Locale("id", "ID"))

      edtDate.text = sdf.millisToString(currentTime.timeInMillis)

      edtDate.setOnClickListener {
        datePicker.show(parentFragmentManager, "Date Picker")
      }

      datePicker.addOnPositiveButtonClickListener {
        edtDate.text = sdf.millisToString(it)
      }
    }
  }

  private fun setupSpinnerListAccount(list: List<AkunModel>) {
    binding?.run {
      fromAccountSpinnerAdapter = TransactionSpinnerAdapter(requireContext(), 0, list)
      toAccountSpinnerAdapter = TransactionSpinnerAdapter(requireContext(), 0, list)

      spFromAccount.apply {
        adapter = fromAccountSpinnerAdapter
      }

      spToAccount.apply {
        adapter = toAccountSpinnerAdapter
      }
    }
  }

  private fun observer() {
    viewModel.getAllAccount()
    viewModel.listAccount.observe(viewLifecycleOwner, ::setupSpinnerListAccount)
  }

  override fun onDestroy() {
    _binding = null
    super.onDestroy()
  }
}