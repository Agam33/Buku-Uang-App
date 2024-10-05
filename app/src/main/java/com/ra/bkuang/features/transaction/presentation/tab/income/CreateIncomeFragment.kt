package com.ra.bkuang.features.transaction.presentation.tab.income

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
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
import com.ra.bkuang.databinding.FragmentCreateIncomeBinding
import com.ra.bkuang.features.account.data.model.AkunModel
import com.ra.bkuang.features.category.data.model.KategoriModel
import com.ra.bkuang.core.data.source.local.database.entity.TransactionType
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import com.ra.bkuang.features.transaction.presentation.TransactionFragment
import com.ra.bkuang.features.transaction.presentation.component.DetailTransactionDialog
import com.ra.bkuang.features.transaction.presentation.tab.income.viewmodel.CreateIncomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.UUID

@AndroidEntryPoint
class CreateIncomeFragment : BaseFragment<FragmentCreateIncomeBinding>(R.layout.fragment_create_income) {

  private val viewModel: CreateIncomeViewModel by viewModels()

  private lateinit var listAccountAdapter: TransactionSpinnerAdapter<AkunModel>
  private lateinit var listCategoryAdapter: TransactionSpinnerAdapter<KategoriModel>

  private lateinit var actionType: ActionType

  private var accountId: UUID? = null
  private var categoryId: UUID? = null

  private lateinit var newIncome: PendapatanModel

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    init()
    observer()
    setupAccountAndCategoryPicker()
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
            createIncome()
          }
        }
      }
      ActionType.EDIT -> {
        binding?.run {
          val uuid = arguments?.getString(DetailTransactionDialog.EXTRA_TRANSACTION_ID) as String
          viewModel.getIncomeById(uuid)
        }
      }
    }

    viewModel.getAllAccount()
    viewModel.setCategoryByType(TransactionType.PENDAPATAN)
  }

  private fun observer() {
    lifecycleScope.launch {
      viewModel.uiState.collect { uiState ->
        setupListAccount(uiState.accountList)
        setupListCategory(uiState.categoryList)

        uiState.incomeModel?.let {
          setupEditIncome(it)
        }

        uiState.isSuccessful?.let {
          if(it) {
            showShortToast(getString(R.string.msg_success))
            activity?.finish()
          } else {
            showShortToast(getString(R.string.msg_failed))
          }
        }
      }
    }
  }

  private fun setupEditIncome(model: PendapatanModel) {
    binding?.run {
      edtAmount.text = Editable.Factory.getInstance().newEditable(model.jumlah.toString())
      edtNote.text = Editable.Factory.getInstance().newEditable(model.deskripsi)

      val calendar = model.updatedAt.toCalendar()
      setupTimePicker(calendar)
      setupDatePicker(calendar)

      btnSave.setOnClickListener {
        updateIncome(model)
      }
    }
  }

  private fun updateIncome(model: PendapatanModel) {
    binding?.run {
      val amount: String = edtAmount.text.toString()
      val note: String = edtNote.text.toString()

      if(amount.isBlank()) {
        showShortToast(getString(R.string.msg_empty))
        return
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

      newIncome = PendapatanModel(
        uuid = model.uuid,
        idKategori = categoryId ?: return@run,
        idAkun = accountId ?: return@run,
        deskripsi = note,
        jumlah = edtAmount.getIntValue(),
        createdAt = model.createdAt,
        updatedAt = createdAt
      )

      viewModel.updateIncome(newIncome, model)
    }
  }

  private fun createIncome() {
    binding?.run {
      val amount: String = edtAmount.text.toString()
      val note: String = edtNote.text.toString()

      if(amount.isBlank()) {
        showShortToast(getString(R.string.msg_empty))
        return
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

      newIncome = PendapatanModel(
        uuid = UUID.randomUUID(),
        idKategori = categoryId ?: return@run,
        idAkun = accountId ?: return@run,
        deskripsi = note,
        jumlah = edtAmount.getIntValue(),
        createdAt = createdAt,
        updatedAt = createdAt
      )

      viewModel.saveIncome(newIncome)
    }
  }

  private fun setupAccountAndCategoryPicker() {
    binding?.run {
      spCategory.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, p3: Long) {
          val model = adapter?.getItemAtPosition(position) as KategoriModel
          categoryId = model.uuid
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
      }

      spAccount.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, p3: Long) {
          val model = adapter?.getItemAtPosition(position) as AkunModel
          accountId = model.uuid
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
      }
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

  private fun setupListAccount(listAccount: List<AkunModel>) {
    binding?.run {
    listAccountAdapter = TransactionSpinnerAdapter(requireContext(), 0, listAccount)
      spAccount.apply {
        adapter = listAccountAdapter
      }
    }
  }

  private fun setupListCategory(listCategory: List<KategoriModel>) {
    binding?.run {
      listCategoryAdapter = TransactionSpinnerAdapter(requireContext(), 0, listCategory)
      spCategory.apply {
        adapter = listCategoryAdapter
      }
    }
  }
}