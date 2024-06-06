package com.ra.bkuang.features.debt.presentation

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ra.bkuang.R
import com.ra.bkuang.common.view.dialog.CalendarDialog
import com.ra.bkuang.databinding.ActivityDetailDebtBinding
import com.ra.bkuang.di.MainDispatcherQualifier
import com.ra.bkuang.features.debt.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.common.base.BaseActivity
import com.ra.bkuang.common.util.ActionType
import com.ra.bkuang.common.util.Constants.DATE_TIME_FORMATTER
import com.ra.bkuang.common.util.Extension.toFormatRupiah
import com.ra.bkuang.common.util.Extension.toPercent
import com.ra.bkuang.common.util.Extension.toPercentText
import com.ra.bkuang.common.util.Extension.toStringFormat
import com.ra.bkuang.common.util.OnItemChangedListener
import com.ra.bkuang.common.util.ResultState
import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.debt.presentation.DebtFragment.Companion.DEBT_EXTRA_ACTION
import com.ra.bkuang.features.debt.presentation.DebtFragment.Companion.DEBT_MODEL
import com.ra.bkuang.features.debt.presentation.adapter.DebtRecordAdapter
import com.ra.bkuang.features.debt.presentation.dialog.AddDebtRecordDialog
import com.ra.bkuang.common.util.Extension.getStringResource
import com.ra.bkuang.common.util.Extension.setupActionBar
import com.ra.bkuang.common.util.Extension.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class DetailDebtActivity : BaseActivity<ActivityDetailDebtBinding>(R.layout.activity_detail_debt),
  OnItemChangedListener, DebtRecordAdapter.OnItemLongClickListener {

  @Inject @MainDispatcherQualifier lateinit var mainDispatcher: CoroutineDispatcher
  @Inject lateinit var debtRecordAdapter: DebtRecordAdapter

  private val viewModel: DetailDebtViewModel by viewModels()

  private var debtModelId: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    debtModelId = intent?.getStringExtra(DebtFragment.DEBT_MODEL_ID)

    setupActionBar(binding.toolbar, isDisplayHomeAsUpEnabled = false)

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
        is ResultState.Success -> {
          viewModel.setState(rvState = false, emptyState =  true)

          val data = it.data

          debtRecordAdapter.submitList(data)

          rvDebtRecord.apply {
            adapter = debtRecordAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
          }

          debtRecordAdapter.onItemLongClickListener = this@DetailDebtActivity
        }
        is ResultState.Empty -> {
          viewModel.setState(rvState = true, emptyState =  false)
        }
        is ResultState.Loading -> {}
        is ResultState.Error -> {}
      }
    }
  }

  private fun setupDetailDebt() = with(binding) {
    lifecycleScope.launch(mainDispatcher) {
      viewModel.getHutangByIdWithFlow(debtModelId ?: "")
        .collect {
          it?.let {
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
          changeAlarmToolbarIcon(it?.pengingatAktif ?: return@collect)
        }
    }
  }

  private fun changeAlarmToolbarIcon(state: Boolean) {
    binding.toolbar.menu.findItem(R.id.menu_debt_alarm)?.setIcon(
      if(state) R.drawable.active_alarm_on_24 else R.drawable.inactive_round_alarm_24
    )
  }

  private fun observer() {
    debtModelId?.let {
      viewModel.getAllDebtRecord(it)
      viewModel.getSizeListPembayaranHutang(it)
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when(item.itemId) {
      R.id.menu_debt_alarm -> {
        lifecycleScope.launch {
          val model = viewModel.getHutangById(debtModelId ?: "")
          setAlarm(model)
        }
        true
      }
      R.id.menu_debt_edit -> {
        lifecycleScope.launch {
          val model = viewModel.getHutangById(debtModelId ?: "")
          editDebt(model)
        }
        true
      }
      R.id.menu_debt_delete -> {
          Snackbar.make(binding.root, getStringResource(R.string.msg_delete), Snackbar.LENGTH_SHORT)
            .setAction(getStringResource(R.string.txt_yes)) {
              lifecycleScope.launch {
                val model = viewModel.getHutangById(debtModelId ?: "")
                if (viewModel.deleteHutang(model)) {
                  finish()
                }
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

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_detail_debt, menu)
    lifecycleScope.launch(mainDispatcher) {
      val model = viewModel.getHutangById(debtModelId ?: "")
      changeAlarmToolbarIcon(model.pengingatAktif)
    }
    return true
  }
}