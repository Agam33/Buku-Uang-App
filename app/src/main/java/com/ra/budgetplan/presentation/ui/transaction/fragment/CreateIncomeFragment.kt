package com.ra.budgetplan.presentation.ui.transaction.fragment

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.ra.budgetplan.R
import com.ra.budgetplan.customview.spinner.TransactionSpinnerAdapter
import com.ra.budgetplan.databinding.FragmentCreateIncomeBinding
import com.ra.budgetplan.domain.entity.TipeKategori
import com.ra.budgetplan.domain.model.AkunModel
import com.ra.budgetplan.domain.model.KategoriModel
import com.ra.budgetplan.domain.model.PendapatanModel
import com.ra.budgetplan.presentation.ui.transaction.TransactionFragment
import com.ra.budgetplan.presentation.viewmodel.TransactionViewModel
import com.ra.budgetplan.util.ActionType
import com.ra.budgetplan.util.DATE_PATTERN
import com.ra.budgetplan.util.DATE_TIME_FORMATTER
import com.ra.budgetplan.util.checkTimeFormat
import com.ra.budgetplan.util.getActionType
import com.ra.budgetplan.util.getStringResource
import com.ra.budgetplan.util.millisToString
import com.ra.budgetplan.util.showShortToast
import com.ra.budgetplan.util.toCalendar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.UUID

@AndroidEntryPoint
class CreateIncomeFragment : Fragment() {

  private var _binding: FragmentCreateIncomeBinding? = null
  private val binding get() = _binding

  private val viewModel: TransactionViewModel by viewModels()

  private lateinit var listAccountAdapter: TransactionSpinnerAdapter<AkunModel>
  private lateinit var listCategoryAdapter: TransactionSpinnerAdapter<KategoriModel>


  private var accountId: UUID? = null
  private var categoryId: UUID? = null

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    _binding = FragmentCreateIncomeBinding.inflate(inflater, container, false)
    observer()
    setupAccountAndCategoryPicker()
    return binding?.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val actionType = arguments?.getString(TransactionFragment.EXTRA_TRANSACTION_CREATE_OR_EDIT) as String
    when(getActionType(actionType)) {
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
          viewModel.getPendapatanById(UUID.fromString(uuid))
          setupEditIncome()
        }
      }
    }
  }

  private fun setupEditIncome() {
    binding?.run {
      viewModel.pendapatanModel.observe(viewLifecycleOwner) { model ->
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
  }

  private fun updateIncome(model: PendapatanModel) {
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

      val pendapatanModel = PendapatanModel(
        uuid = model.uuid,
        idKategori = categoryId ?: return@run,
        idAkun = accountId ?: return@run,
        deskripsi = note,
        jumlah = amount.toInt(),
        createdAt = model.createdAt,
        updatedAt = createdAt
      )

      viewModel.updatePendapatan(pendapatanModel, model)

      showShortToast(getString(R.string.msg_success))

      activity?.finish()
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

      val timeStringBuilder = StringBuilder()
      timeStringBuilder.append(edtDate.text.trim())
      timeStringBuilder.append(" ")
      timeStringBuilder.append(btnTime.text.trim())
      val dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)
      val createdAt = LocalDateTime.parse(timeStringBuilder.toString(), dateTimeFormatter)

      val pendepatanModel = PendapatanModel(
        uuid = UUID.randomUUID(),
        idKategori = categoryId ?: return@run,
        idAkun = accountId ?: return@run,
        deskripsi = note,
        jumlah = amount.toInt(),
        createdAt = createdAt,
        updatedAt = createdAt
      )

      viewModel.savePendapatan(pendepatanModel)

      showShortToast(getString(R.string.msg_success))

      activity?.finish()
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

  private fun observer() {
    viewModel.getAllAccount()
    viewModel.setCategoryByType(TipeKategori.PENDAPATAN)
    viewModel.listCategoryByType.observe(viewLifecycleOwner, ::setupListCategory)
    viewModel.listAccount.observe(viewLifecycleOwner, ::setupListAccount)
  }

  override fun onDestroy() {
    _binding = null
    Timber.tag("CreateIncomeFragment").d("onDestroy()")
    super.onDestroy()
  }
}