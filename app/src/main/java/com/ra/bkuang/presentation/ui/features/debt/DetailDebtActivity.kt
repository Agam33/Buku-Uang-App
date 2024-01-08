package com.ra.bkuang.presentation.ui.features.debt

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.bkuang.R
import com.ra.bkuang.databinding.ActivityDetailDebtBinding
import com.ra.bkuang.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.presentation.ui.base.BaseActivity
import com.ra.bkuang.presentation.viewmodel.DetailDebtViewModel
import com.ra.bkuang.presentation.util.ActionType
import com.ra.bkuang.presentation.util.Constants.DATE_TIME_FORMATTER
import com.ra.bkuang.presentation.util.Extension.toFormatRupiah
import com.ra.bkuang.presentation.util.Extension.toPercent
import com.ra.bkuang.presentation.util.Extension.toPercentText
import com.ra.bkuang.presentation.util.Extension.toStringFormat
import com.ra.bkuang.presentation.util.OnItemChangedListener
import com.ra.bkuang.domain.util.Resource
import com.ra.bkuang.domain.util.ResourceState
import com.ra.bkuang.presentation.ui.features.debt.DebtFragment.Companion.DEBT_EXTRA_ACTION
import com.ra.bkuang.presentation.ui.features.debt.DebtFragment.Companion.DEBT_MODEL
import com.ra.bkuang.presentation.ui.features.debt.adapter.DebtRecordAdapter
import com.ra.bkuang.presentation.ui.features.debt.dialog.AddDebtRecordDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.UUID

@AndroidEntryPoint
class DetailDebtActivity : BaseActivity<ActivityDetailDebtBinding>(R.layout.activity_detail_debt),
  OnItemChangedListener, DebtRecordAdapter.OnItemLongClickListener {

  private val viewModel: DetailDebtViewModel by viewModels()

  private var debtModelId: UUID? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    debtModelId = UUID.fromString(intent?.getStringExtra(DebtFragment.DEBT_MODEL_ID))

    binding.vm = viewModel
    binding.lifecycleOwner = this

    observer()
    setupDetailDebt()
    setupListExpense()
    setupButton()
  }

  private fun setupButton() = with(binding) {
    btnAdd.setOnClickListener {
      val args = Bundle().apply {
        putString(DEBT_EXTRA_ACTION, ActionType.CREATE.name)
        putString(DebtFragment.DEBT_MODEL_ID, debtModelId.toString())
      }

      val createDebtRecord = AddDebtRecordDialog().apply {
        arguments = args
        onItemChangedListener = this@DetailDebtActivity
      }

      createDebtRecord.show(supportFragmentManager, "create-debt-record")
    }
  }

  private fun setupListExpense() = with(binding) {
    viewModel.debtRecord.observe(this@DetailDebtActivity) {
      when(it) {
        is Resource.Success -> {
          viewModel.setState(rvState = false, emptyState =  true)

          val data = it.data
          val debRecordAdapter = DebtRecordAdapter()
          debRecordAdapter.submitList(data)

          rvDebtRecord.apply {
            adapter = debRecordAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
          }

          debRecordAdapter.onItemLongClickListener = this@DetailDebtActivity
        }

        is Resource.Empty -> {
          viewModel.setState(rvState = true, emptyState =  false)
        }

        is Resource.Loading -> {}
      }
    }
  }

  private fun setupDetailDebt() = with(binding) {
    lifecycleScope.launch {
      viewModel.getHutangByIdWithFlow(debtModelId ?: return@launch).collect {
        val percent = it.totalPengeluaran.toPercent(it.maxCicilan)
        tvPercent.text = percent.toPercentText()
        goalProgress.progress = percent.toInt()
        tvCurrentMoney.text = it.totalPengeluaran.toFormatRupiah()
        tvGoalMoney.text = it.maxCicilan.toFormatRupiah()
        tvDebtTitle.text = it.nama
        tvDueDate.text = String.format(binding.root.context.getString(R.string.txt_due_date_format), it.jatuhTempo.toStringFormat(
          DATE_TIME_FORMATTER
        ))
      }
    }
  }

  private fun observer() {
    debtModelId?.let {
      viewModel.getHutangById(it)
      viewModel.getAllDebtRecord(it)
      viewModel.getSizeListPembayaranHutang(it)
    }
  }

  override fun onItemChanged() {
    observer()
  }

  override fun onItemDelete(model: DetailPembayaranHutangModel) {
    lifecycleScope.launch {
      viewModel.deleteRecordPembayaranHutang(model).collect { status ->
        when(status) {
          ResourceState.LOADING -> {}
          ResourceState.SUCCESS -> {
            observer()
          }
          ResourceState.FAILED -> {}
        }
      }
    }
  }

  override fun onItemUpdate(model: DetailPembayaranHutangModel) {
    val args = Bundle().apply {
      putParcelable(DEBT_MODEL, model.pembayaranHutangModel)
      putString(DEBT_EXTRA_ACTION, ActionType.EDIT.name)
    }

    val createDebtRecord = AddDebtRecordDialog().apply {
      arguments = args
      onItemChangedListener = this@DetailDebtActivity
    }

    createDebtRecord.show(supportFragmentManager, "create-debt-record")
  }
}