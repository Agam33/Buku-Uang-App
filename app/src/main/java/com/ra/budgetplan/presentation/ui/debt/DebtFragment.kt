package com.ra.budgetplan.presentation.ui.debt

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ra.budgetplan.R
import com.ra.budgetplan.base.BaseFragment
import com.ra.budgetplan.customview.dialog.CalendarDialog
import com.ra.budgetplan.customview.dialog.CautionDeleteDialog
import com.ra.budgetplan.customview.dialog.CautionDeleteDialog.Companion.MSG_CAUTION_DIALOG
import com.ra.budgetplan.databinding.FragmentDebtBinding
import com.ra.budgetplan.domain.model.HutangModel
import com.ra.budgetplan.presentation.ui.debt.adapter.DebtAdapter
import com.ra.budgetplan.presentation.viewmodel.DebtViewModel
import com.ra.budgetplan.receiver.AlarmReceiver
import com.ra.budgetplan.util.ActionType
import com.ra.budgetplan.util.Constants.DATE_PATTERN
import com.ra.budgetplan.util.Constants.LOCALE_ID
import com.ra.budgetplan.util.Extension.showShortToast
import com.ra.budgetplan.util.Resource
import com.ra.budgetplan.util.StatusItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

@AndroidEntryPoint
class DebtFragment : BaseFragment<FragmentDebtBinding>(R.layout.fragment_debt) {

  private val sharedViewModel: DebtViewModel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding?.vm = sharedViewModel
    binding?.lifecycleOwner = viewLifecycleOwner
    setupRv()
    createNewDebt()
  }

  private fun createNewDebt() {
    binding?.fabAddDebt?.setOnClickListener {
      val i = Intent(requireContext(), CreateDebtActivity::class.java).apply {
        putExtra(DEBT_EXTRA_ACTION, ActionType.CREATE.name)
      }
      startActivity(i)
    }
  }

  private fun setupRv() {
    refresh()
    sharedViewModel.hutangList.observe(viewLifecycleOwner) {
      when(it) {
        is Resource.Empty -> {
          sharedViewModel.setState(rvState = true, emptyState = false)
        }

        is Resource.Success -> {
          sharedViewModel.setState(rvState = false, emptyState = true)
          setupList(it.data)
        }

        is Resource.Loading -> {}
      }
    }
  }

  private fun refresh() {
    sharedViewModel.getHutangList()
  }

  private fun setupList(list: List<HutangModel>?) {
    val debtAdapter = DebtAdapter()
    debtAdapter.submitList(list)

    debtAdapter.setItemClickListener = object : DebtAdapter.OnItemClickListener {
      override fun setOnItemClickCallback(model: HutangModel) {
        val i = Intent(requireContext(), DetailDebtActivity::class.java).apply {
          putExtra(DEBT_MODEL_ID, model.uuid.toString())
        }
        startActivity(i)
      }
    }

    debtAdapter.setIconDeleteListener = object : DebtAdapter.OnIconDeleteListener {

      override fun setOnItemDelete(model: HutangModel, adapterPosition: Int) {
        val arg = Bundle()
        arg.putString(MSG_CAUTION_DIALOG, requireContext().resources.getString(R.string.msg_delete_debt))

        val cautionDeleteDialog = CautionDeleteDialog().apply {
          arguments  = arg
        }

        cautionDeleteDialog.onOptionItemClick = object : CautionDeleteDialog.OnOptionItemClick {

          override fun onDelete() {
            viewLifecycleOwner.lifecycleScope.launch {
              sharedViewModel.deleteHutang(model).collect { status ->
                when(status) {
                  StatusItem.LOADING -> {}
                  StatusItem.SUCCESS -> {
                    showShortToast(requireContext().resources.getString(R.string.msg_success))
                    refresh()
                    cautionDeleteDialog.dismiss()
                  }
                  StatusItem.FAILED -> {}
                }
              }
            }
          }

          override fun onCancel() { cautionDeleteDialog.dismiss() }
        }

        cautionDeleteDialog.show(childFragmentManager, "caution-delete-dialog")
      }
    }

    debtAdapter.setIconAlarmListener = object : DebtAdapter.OnIconAlarmListener {
      override fun setOnItemAlarm(model: HutangModel, adapterPosition: Int) {
        val alarmId = model.uuid.hashCode()

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java).apply {
          putExtra(DEBT_MODEL_ID, model.uuid.toString())
          putExtra(DEBT_ALARM_EXTRA_TITLE, model.nama)
          putExtra(DEBT_ALARM_EXTRA_ID, alarmId)
        }

        val pendingIntent = PendingIntent.getBroadcast(
          requireContext(),
          alarmId,
          intent,
          PendingIntent.FLAG_MUTABLE
        )

        if(!model.pengingatAktif) {
          val calendarDialog = CalendarDialog()
          calendarDialog.onSetAlarmListener = object : CalendarDialog.OnSetAlarmListener {

            override fun onSave(calendar: Calendar) {
              val sdf = SimpleDateFormat(DATE_PATTERN, LOCALE_ID)
              model.idPengingat = alarmId
              model.pengingatAktif = true
              model.tglPengingat = sdf.format(calendar.time)

              lifecycleScope.launch {
                sharedViewModel.updateHutang(model).collect { status ->
                  when(status) {
                    StatusItem.LOADING -> {}
                    StatusItem.SUCCESS -> {
                      alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                      debtAdapter.notifyItemChanged(adapterPosition)
                      showShortToast(requireContext().getString(R.string.msg_success))
                    }
                    StatusItem.FAILED -> {}
                  }
                }
              }
              calendarDialog.dismiss()
            }
            override fun onCancel() {}
          }
          calendarDialog.show(childFragmentManager, "set-alarm-dialog")
        } else {
          Snackbar.make(view!!, requireContext().getString(R.string.msg_cancel_alarm_schedule), Snackbar.LENGTH_SHORT)
            .setAction(requireContext().getString(R.string.txt_yes)) {

              model.idPengingat = alarmId
              model.pengingatAktif = false
              model.tglPengingat = ""

              lifecycleScope.launch {
                sharedViewModel.updateHutang(model).collect { status ->
                  when(status) {
                    StatusItem.LOADING -> {}
                    StatusItem.SUCCESS -> {
                      alarmManager.cancel(pendingIntent)
                      debtAdapter.notifyItemChanged(adapterPosition)
                    }
                    StatusItem.FAILED -> {}
                  }
                }
              }

              debtAdapter.notifyItemChanged(adapterPosition)
            }
            .show()
        }
      }
    }

    debtAdapter.setIconEditListener = object : DebtAdapter.OnIconEditListener {
      override fun setOnItemEdit(model: HutangModel) {
        val i = Intent(requireContext(), CreateDebtActivity::class.java).apply {
          putExtra(DEBT_EXTRA_ACTION, ActionType.EDIT.name)
          putExtra(DEBT_MODEL, model)
        }
        startActivity(i)
      }
    }

    binding?.rvDebtList?.apply {
      adapter = debtAdapter
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(context)
    }
  }

  override fun onStart() {
    super.onStart()
    refresh()
  }

  companion object {
    const val DEBT_EXTRA_ACTION = "debt-extra-action"
    const val DEBT_MODEL = "debt-model"
    const val DEBT_MODEL_ID = "debt-model-id"
    const val DEBT_ALARM_EXTRA_TITLE = "debt-alarm-extra-title"
    const val DEBT_ALARM_EXTRA_ID = "debt-alarm-extra-id"
  }
}