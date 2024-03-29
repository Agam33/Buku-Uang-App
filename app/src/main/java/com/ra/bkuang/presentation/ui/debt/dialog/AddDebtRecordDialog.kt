package com.ra.bkuang.presentation.ui.debt.dialog

import android.app.Dialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.ra.bkuang.R
import com.ra.bkuang.custom.spinner.TransactionSpinnerAdapter
import com.ra.bkuang.domain.model.AkunModel
import com.ra.bkuang.domain.model.PembayaranHutangModel
import com.ra.bkuang.presentation.ui.debt.DetailDebtViewModel
import com.ra.bkuang.util.ActionType
import com.ra.bkuang.util.Constants.DATE_PATTERN
import com.ra.bkuang.util.Constants.DATE_TIME_FORMATTER
import com.ra.bkuang.util.Constants.LOCALE_ID
import com.ra.bkuang.util.Extension.checkTimeFormat
import com.ra.bkuang.util.Extension.getStringResource
import com.ra.bkuang.util.Extension.millisToString
import com.ra.bkuang.util.Extension.parcelable
import com.ra.bkuang.util.Extension.showShortToast
import com.ra.bkuang.util.Extension.toCalendar
import com.ra.bkuang.util.OnItemChangedListener
import com.ra.bkuang.util.ResourceState
import com.ra.bkuang.presentation.ui.debt.DebtFragment
import com.ra.bkuang.presentation.ui.debt.DebtFragment.Companion.DEBT_MODEL
import com.ra.bkuang.util.getActionType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.UUID

@AndroidEntryPoint
class AddDebtRecordDialog: DialogFragment() {

  private lateinit var accountSpinnerAdapter: TransactionSpinnerAdapter<AkunModel>

  private var accountId: UUID? = null

  private val viewModel: DetailDebtViewModel by viewModels()

  var onItemChangedListener: OnItemChangedListener? = null

  private lateinit var spAccount: Spinner
  private lateinit var edtAmount: TextInputEditText
  private lateinit var btnSave: Button
  private lateinit var btnCancel: Button
  private lateinit var btnDate: MaterialButton
  private lateinit var btnTime: MaterialButton

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val inflate = activity?.layoutInflater
    val materialDialog = MaterialAlertDialogBuilder(requireContext())
    val view = inflate?.inflate(R.layout.add_debt_record_dialog, null)

    materialDialog.setView(view)

    view?.let {
      initView(it)
    }

    setupButton()

    val actionType = arguments?.getString(DebtFragment.DEBT_EXTRA_ACTION) as String
    when(getActionType(actionType)) {
      ActionType.EDIT -> {
        val model = arguments?.parcelable<PembayaranHutangModel>(DEBT_MODEL)
        setupEditModel(model)
      }
      ActionType.CREATE -> {
        val calendar = Calendar.getInstance()
        setupTimePicker(calendar)
        setupDatePicker(calendar)
      }
    }

    observer()

    return materialDialog.create()
  }

  private fun initView(v: View) {
    spAccount = v.findViewById(R.id.sp_account)
    edtAmount = v.findViewById(R.id.edt_amount)
    btnSave = v.findViewById(R.id.btn_save)
    btnCancel = v.findViewById(R.id.btn_cancel)
    btnDate = v.findViewById(R.id.btn_date)
    btnTime = v.findViewById(R.id.btn_time)
  }

  private fun observer() {
    viewModel.getAllAccount()
    viewModel.accounts.observe(requireActivity(), ::setupListAccount)
  }

  private fun setupButton() {
    btnSave.setOnClickListener {
      validateInput()
    }
    btnCancel.setOnClickListener {
      dismiss()
    }
  }

  private fun validateInput() {
    val amount = edtAmount.text.toString()

    if(amount.length > 9) {
      showShortToast(getString(R.string.msg_too_over))
      return
    }

    if(amount.isBlank()) {
      showShortToast(getString(R.string.msg_empty))
      return
    }

    val timeStringBuilder = StringBuilder()
    timeStringBuilder.append(btnDate.text.trim())
    timeStringBuilder.append(" ")
    timeStringBuilder.append(btnTime.text.trim())
    val dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)
    val updated = LocalDateTime.parse(timeStringBuilder.toString(), dateTimeFormatter)

    val actionType = arguments?.getString(DebtFragment.DEBT_EXTRA_ACTION) as String
    when(getActionType(actionType)) {
      ActionType.EDIT -> {
        val detailPembayaranDebtModel = arguments?.parcelable<PembayaranHutangModel>(DEBT_MODEL)!!

        val oldModel = detailPembayaranDebtModel.copy()

        detailPembayaranDebtModel.apply {
          jumlah = amount.toInt()
          updatedAt = updated
          idAkun = accountId ?: idAkun
        }

        requireActivity().lifecycleScope.launch {
          viewModel.updatePembayaranHutang(detailPembayaranDebtModel, oldModel).collect { status ->
            when(status) {
              ResourceState.LOADING -> {}
              ResourceState.SUCCESS -> {
                onItemChangedListener?.onItemChanged()
                dismiss()
              }
              ResourceState.FAILED -> {}
            }
          }
        }
      }

      ActionType.CREATE -> {
        val debtModelId = arguments?.getString(DebtFragment.DEBT_MODEL_ID)
        val paymentDebtModel = PembayaranHutangModel(
          uuid = UUID.randomUUID(),
          idAkun = accountId ?: return,
          idHutang = UUID.fromString(debtModelId),
          jumlah = amount.toInt(),
          createdAt = LocalDateTime.now(),
          updatedAt = updated
        )

        requireActivity().lifecycleScope.launch {
          viewModel.savePembayaranHutang(paymentDebtModel).collect { status ->
            when(status) {
              ResourceState.LOADING -> {}
              ResourceState.SUCCESS -> {
                onItemChangedListener?.onItemChanged()
                dismiss()
              }
              ResourceState.FAILED -> {}
            }
          }
        }
      }
    }
  }

  private fun setupEditModel(model: PembayaranHutangModel?) {
    model?.let {
      edtAmount.text = Editable.Factory.getInstance().newEditable(it.jumlah.toString())

      val calendar = it.updatedAt.toCalendar()
      setupTimePicker(calendar)
      setupDatePicker(calendar)
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

    btnTime.text = requireContext()
      .getStringResource(
        R.string.txt_hour_and_minute,
        currentHour.checkTimeFormat(),
        currMinute.checkTimeFormat()
      )

    timePicker.addOnPositiveButtonClickListener {
      btnTime.text = requireContext()
        .getStringResource(
          R.string.txt_hour_and_minute,
          timePicker.hour.checkTimeFormat(),
          timePicker.minute.checkTimeFormat()
        )
    }

    btnTime.setOnClickListener {
      timePicker.show(childFragmentManager, "Time Picker")
    }
  }

  private fun setupDatePicker(calendar: Calendar) {
    val datePicker = MaterialDatePicker.Builder.datePicker()
      .setTitleText(getString(R.string.msg_date_picker))
      .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
      .build()

    val sdf = SimpleDateFormat(DATE_PATTERN, LOCALE_ID)

    btnDate.text = sdf.millisToString(calendar.timeInMillis)

    btnDate.setOnClickListener {
      datePicker.show(childFragmentManager, "Date Picker")
    }

    datePicker.addOnPositiveButtonClickListener {
      btnDate.text = sdf.millisToString(it)
    }
  }

  private fun setupListAccount(list: List<AkunModel>) {
    accountSpinnerAdapter = TransactionSpinnerAdapter(requireContext(), 0, list)
    spAccount.apply {
      adapter = accountSpinnerAdapter
    }
    spAccount.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
      override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, p3: Long) {
        val model = adapter?.getItemAtPosition(position) as AkunModel
        accountId = model.uuid
      }

      override fun onNothingSelected(p0: AdapterView<*>?) {}
    }
  }

  override fun onDestroyView() {
    viewModel.accounts.removeObservers(requireActivity())
    super.onDestroyView()
  }
}