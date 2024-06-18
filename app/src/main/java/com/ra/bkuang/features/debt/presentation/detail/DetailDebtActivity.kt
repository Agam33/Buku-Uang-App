package com.ra.bkuang.features.debt.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseActivity
import com.ra.bkuang.common.util.ActionType
import com.ra.bkuang.common.util.Constants.DATE_TIME_FORMATTER
import com.ra.bkuang.common.util.Extension.getStringResource
import com.ra.bkuang.common.util.Extension.hide
import com.ra.bkuang.common.util.Extension.setupActionBar
import com.ra.bkuang.common.util.Extension.showShortToast
import com.ra.bkuang.common.util.Extension.toFormatRupiah
import com.ra.bkuang.common.util.Extension.toPercent
import com.ra.bkuang.common.util.Extension.toPercentText
import com.ra.bkuang.common.util.Extension.toStringFormat
import com.ra.bkuang.common.view.dialog.CalendarDialog
import com.ra.bkuang.databinding.ActivityDetailDebtBinding
import com.ra.bkuang.features.debt.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.presentation.CreateDebtActivity
import com.ra.bkuang.features.debt.presentation.DebtFragment
import com.ra.bkuang.features.debt.presentation.DebtFragment.Companion.DEBT_EXTRA_ACTION
import com.ra.bkuang.features.debt.presentation.DebtFragment.Companion.DEBT_MODEL
import com.ra.bkuang.features.debt.presentation.adapter.DebtRecordAdapter
import com.ra.bkuang.features.debt.presentation.detail.viewmodel.DetailDebtViewModel
import com.ra.bkuang.features.debt.presentation.dialog.AddDebtRecordDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class DetailDebtActivity : BaseActivity<ActivityDetailDebtBinding>(R.layout.activity_detail_debt),
   DebtRecordAdapter.OnItemLongClickListener {

  private val viewModel: DetailDebtViewModel by viewModels()

  private var debtModelId: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    debtModelId = intent?.getStringExtra(DebtFragment.DEBT_MODEL_ID)

    init()

    setupActionBar(
      binding.toolbar,
      isDisplayHomeAsUpEnabled = false
    )

    observer()
    setupButton()
  }

  private fun observer() {
    lifecycleScope.launch {
      viewModel.uiState.collect { uiState ->

        setupPaidDebtList(uiState.paidDebtRecord)

        uiState.isSuccessfulDeleteDetail?.let {
          if(it) {
            finish()
          }
        }

        uiState.isSuccessfulDeleteRecord?.let {
          if(it) {
            showShortToast(getString(R.string.msg_success))
          }
        }

        binding.totalList.text = String.format(getString(R.string.total_debt_record), uiState.sizeDebtRecord)

        setupDetailDebt(uiState.detailDebt)
      }
    }
  }

  private fun setupButton() = with(binding) {
    btnAdd.setOnClickListener {
      val args = Bundle().apply {
        putString(DEBT_EXTRA_ACTION, ActionType.CREATE.name)
        putString(DebtFragment.DEBT_MODEL_ID, debtModelId.toString())
      }

      val createDebtRecord = AddDebtRecordDialog().apply {
        arguments = args
      }

      createDebtRecord.show(supportFragmentManager, "create-debt-record")
    }
  }

  private fun changeAlarmToolbarIcon(state: Boolean) {
    binding.toolbar.menu.findItem(R.id.menu_debt_alarm)?.setIcon(
      if(state) R.drawable.active_alarm_on_24 else R.drawable.inactive_round_alarm_24
    )
  }

  private fun init() {
    debtModelId?.let {
      viewModel.getAllDebtRecord(it)
      viewModel.getSizeListPaidDebt(it)
    }
    viewModel.getDebtByIdWithFlow(debtModelId ?: "")
  }

  private fun setupPaidDebtList(paidDebtRecord: List<DetailPembayaranHutangModel>) {
    if(paidDebtRecord.isNotEmpty()) {
      binding.rvDebtRecord.hide(false)
      binding.emptyLayout.state = true

      val debtRecordAdapter = DebtRecordAdapter().apply {
        onItemLongClickListener = this@DetailDebtActivity
      }

      debtRecordAdapter.submitList(paidDebtRecord)

      binding.rvDebtRecord.apply {
        adapter = debtRecordAdapter
        layoutManager = LinearLayoutManager(this@DetailDebtActivity)
        setHasFixedSize(true)
      }

    } else {
      binding.rvDebtRecord.hide(true)
      binding.emptyLayout.state = false
    }
  }

  private fun setupDetailDebt(it: HutangModel?) {
    if(it == null) return
    val percent = it.totalPengeluaran.toPercent(it.maxCicilan)
    binding.tvPercent.text = percent.toPercentText()
    binding.goalProgress.progress = percent.toInt()
    binding.tvCurrentMoney.text = it.totalPengeluaran.toFormatRupiah()
    binding.tvGoalMoney.text = it.maxCicilan.toFormatRupiah()
    binding.tvDebtTitle.text = it.nama
    binding.tvDueDate.text = String.format(binding.root.context.getString(R.string.txt_due_date_format), it.jatuhTempo.toStringFormat(
      DATE_TIME_FORMATTER
    ))
    changeAlarmToolbarIcon(it.pengingatAktif)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when(item.itemId) {
      R.id.menu_debt_alarm -> {
        lifecycleScope.launch {
          val model = viewModel.getDebtById(debtModelId ?: "")
          setAlarm(model)
        }
        true
      }
      R.id.menu_debt_edit -> {
        lifecycleScope.launch {
          val model = viewModel.getDebtById(debtModelId ?: "")
          editDebt(model)
        }
        true
      }
      R.id.menu_debt_delete -> {
          Snackbar.make(binding.root, getStringResource(R.string.msg_delete), Snackbar.LENGTH_SHORT)
            .setAction(getStringResource(R.string.txt_yes)) {
              lifecycleScope.launch {
                val model = viewModel.getDebtById(debtModelId ?: "")
                viewModel.deleteDebt(model)
              }
            }.show()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun editDebt(model: HutangModel) {
    val i = Intent(this@DetailDebtActivity, CreateDebtActivity::class.java).apply {
      putExtra(DEBT_EXTRA_ACTION, ActionType.EDIT.name)
      putExtra(DEBT_MODEL, model)
    }
    startActivity(i)
  }

  private fun setAlarm(model: HutangModel) {
    if(!model.pengingatAktif) {
      val calendarDialog = CalendarDialog()
      calendarDialog.onSetAlarmListener = object : CalendarDialog.OnSetAlarmListener {

        override fun onSave(calendar: Calendar) {
          lifecycleScope.launch {
            if(viewModel.setAlarmDebt(calendar, model)) {
              showShortToast(getStringResource(R.string.msg_success))
            }
            calendarDialog.dismiss()
          }
        }
        override fun onCancel() {}
      }
      calendarDialog.show(supportFragmentManager, "set-alarm-dialog")
    } else {
      Snackbar.make(
        binding.root,
        getStringResource((R.string.msg_cancel_alarm_schedule)),
        Snackbar.LENGTH_SHORT
      ).setAction(getStringResource(R.string.txt_yes)) {
          viewModel.cancelAlarmDebt(model)
        }.show()
    }
  }

  override fun onItemDelete(model: DetailPembayaranHutangModel) {
    viewModel.deletePaidDebtRecord(model)
  }

  override fun onItemUpdate(model: DetailPembayaranHutangModel) {
    val args = Bundle().apply {
      putParcelable(DEBT_MODEL, model.pembayaranHutangModel)
      putString(DEBT_EXTRA_ACTION, ActionType.EDIT.name)
    }

    val createDebtRecord = AddDebtRecordDialog().apply {
      arguments = args
    }

    createDebtRecord.show(supportFragmentManager, "create-debt-record")
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_detail_debt, menu)
    lifecycleScope.launch {
      val model = viewModel.getDebtById(debtModelId ?: "")
      changeAlarmToolbarIcon(model.pengingatAktif)
    }
    return true
  }
}