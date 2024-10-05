package com.ra.bkuang.features.transaction.presentation.tab.transfer

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseFragment
import com.ra.bkuang.common.util.ActionType
import com.ra.bkuang.common.util.Constants.DATE_PATTERN
import com.ra.bkuang.common.util.Constants.DATE_TIME_FORMATTER
import com.ra.bkuang.common.util.Extension.checkTimeFormat
import com.ra.bkuang.common.util.Extension.getStringResource
import com.ra.bkuang.common.util.Extension.millisToString
import com.ra.bkuang.common.util.Extension.showShortToast
import com.ra.bkuang.common.util.Extension.toCalendar
import com.ra.bkuang.common.util.getActionType
import com.ra.bkuang.common.view.spinner.TransactionSpinnerAdapter
import com.ra.bkuang.databinding.FragmentCreateTransferBinding
import com.ra.bkuang.features.account.data.model.AkunModel
import com.ra.bkuang.features.transaction.domain.model.TransferModel
import com.ra.bkuang.features.transaction.presentation.TransactionFragment
import com.ra.bkuang.features.transaction.presentation.component.DetailTransactionDialog
import com.ra.bkuang.features.transaction.presentation.tab.transfer.viewmodel.CreateTransferViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.UUID

@AndroidEntryPoint
class CreateTransferFragment : BaseFragment<FragmentCreateTransferBinding>(R.layout.fragment_create_transfer) {

  private val viewModel: CreateTransferViewModel by viewModels()

  private lateinit var fromAccountSpinnerAdapter: TransactionSpinnerAdapter<AkunModel>
  private lateinit var toAccountSpinnerAdapter: TransactionSpinnerAdapter<AkunModel>

  private var fromAccountId: UUID? = null
  private var toAccountId: UUID? = null

  private lateinit var newTransferModel: TransferModel
  private lateinit var oldTransferModel: TransferModel

  private lateinit var actionType: ActionType

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    init()
    observer()
    setupAccountPicker()
  }

  private fun init() {
    val type = arguments?.getString(TransactionFragment.EXTRA_TRANSACTION_CREATE_OR_EDIT) as String
    actionType = getActionType(type)
    when(actionType) {
      ActionType.CREATE -> {
        val calendar = Calendar.getInstance()
        setupDatePicker(calendar)
        setupTimePicker(calendar)
        binding?.run {
          btnSave.setOnClickListener {
            createTransfer()
          }
        }
      }
      ActionType.EDIT -> {
        binding?.run {
          val uuid = arguments?.getString(DetailTransactionDialog.EXTRA_TRANSACTION_ID) as String
          viewModel.getTransferById(uuid)
        }
      }
    }
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

  private fun setupEditTransfer(model: TransferModel) {
    binding?.run {
      edtAmount.text = Editable.Factory.getInstance().newEditable(model.jumlah.toString())
      edtNote.text = Editable.Factory.getInstance().newEditable(model.deskripsi)

      val calendar = model.updatedAt.toCalendar()
      setupTimePicker(calendar)
      setupDatePicker(calendar)

      btnSave.setOnClickListener {
        updateTransfer(model)
      }
    }
  }

  private fun updateTransfer(model: TransferModel) {
    binding?.run {

      oldTransferModel = model

      val amount: String = edtAmount.text.toString()
      val note: String = edtNote.text.toString()

      if(amount.isBlank()) {
        showShortToast(getString(R.string.msg_empty))
        return
      }

      if(isSameAccount(
          fromAccountId ?: return@run,
          toAccountId ?: return@run)
      ) {
        showShortToast(requireContext().getString(R.string.txt_same_account))
        return@run
      }

      if(edtAmount.isMaximumInput) {
        showShortToast(String.format(getString(R.string.msg_max_input), edtAmount.maxInput))
        return@run
      }

      val timeStringBuilder = StringBuilder()
      timeStringBuilder.append(edtDate.text.trim())
      timeStringBuilder.append(" ")
      timeStringBuilder.append(btnTime.text.trim())
      val dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)
      val createdAt = LocalDateTime.parse(timeStringBuilder.toString(), dateTimeFormatter)

      newTransferModel = TransferModel(
        uuid = model.uuid,
        jumlah = edtAmount.getIntValue(),
        createdAt = model.createdAt,
        updatedAt = createdAt,
        deskripsi = note,
        idFromAkun = fromAccountId ?: return@run,
        idToAkun = toAccountId ?: return@run
      )

      viewModel.checkAccountMoney(
        fromAccountId ?: return@run,
        amount.toInt()
      )
    }
  }

  private fun createTransfer() {
    binding?.run {
      val amount: String = edtAmount.text.toString()
      val note: String = edtNote.text.toString()

      if(amount.isBlank()) {
        showShortToast(getString(R.string.msg_empty))
        return
      }

      if(isSameAccount(
          fromAccountId ?: return@run ,
          toAccountId ?: return@run)
      ) {
        showShortToast(requireContext().getString(R.string.txt_same_account))
        return@run
      }

      if(edtAmount.isMaximumInput) {
        showShortToast(String.format(getString(R.string.msg_max_input), edtAmount.maxInput))
        return@run
      }

      val timeStringBuilder = StringBuilder()
      timeStringBuilder.append(edtDate.text.trim())
      timeStringBuilder.append(" ")
      timeStringBuilder.append(btnTime.text.trim())
      val dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)
      val createdAt = LocalDateTime.parse(timeStringBuilder.toString(), dateTimeFormatter)

      newTransferModel = TransferModel(
        uuid = UUID.randomUUID(),
        jumlah = edtAmount.getIntValue(),
        createdAt = createdAt,
        updatedAt = createdAt,
        deskripsi = note,
        idFromAkun = fromAccountId ?: return@run,
        idToAkun = toAccountId ?: return@run
      )

      viewModel.checkAccountMoney(
        fromAccountId ?: return@run,
        amount.toInt()
      )
    }
  }

  private fun setupTimePicker(calendar: Calendar) {
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendar.get(Calendar.MINUTE)

    val timePicker = MaterialTimePicker.Builder()
      .setTitleText(getString(R.string.msg_time_picker))
      .setHour(calendar.get(Calendar.HOUR_OF_DAY))
      .setMinute(calendar.get(Calendar.MINUTE))
      .setTimeFormat(TimeFormat.CLOCK_24H)
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

  private fun setupDatePicker(calendar: Calendar) {
    binding?.run {
      val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText(getString(R.string.msg_date_picker))
        .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
        .build()

      val sdf = SimpleDateFormat(DATE_PATTERN, Locale("id", "ID"))

      edtDate.text = sdf.millisToString(calendar.timeInMillis)

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

  private fun isSameAccount(fromAccountId: UUID, toAccountId: UUID): Boolean =
    fromAccountId == toAccountId

  private fun observer() {
    viewModel.getAllAccount()

    lifecycleScope.launch {
      viewModel.uiState.collect { uiState ->
        setupSpinnerListAccount(uiState.accountList)

        uiState.transferModel?.let {
          setupEditTransfer(it)
        }

        uiState.isSuccessful?.let {
          if(it) {
            showShortToast(getString(R.string.msg_success))
            activity?.finish()
          } else {
            showShortToast(getString(R.string.msg_failed))
          }
        }

        if(uiState.showSaveAlert) {
          showSaveAlert()
        }

        if(uiState.isSave) {
          when(actionType) {
            ActionType.CREATE -> viewModel.saveTransfer(newTransferModel)
            ActionType.EDIT -> viewModel.updateTransfer(newTransferModel, oldTransferModel)
          }
        }
      }
    }
  }

  private fun showSaveAlert() {
    MaterialAlertDialogBuilder(requireContext())
      .setMessage(requireContext().resources.getString(R.string.txt_account_minus))
      .setPositiveButton(requireContext().resources.getString(R.string.txt_continue)) { _, _ ->
        viewModel.showSaveAlert(false)
        viewModel.onSave(true)
      }
      .setOnDismissListener {
        viewModel.showSaveAlert(false)
      }
      .create()
      .show()
  }
}