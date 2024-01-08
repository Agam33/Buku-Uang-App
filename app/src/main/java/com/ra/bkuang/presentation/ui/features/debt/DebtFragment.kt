package com.ra.bkuang.presentation.ui.features.debt

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ra.bkuang.R
import com.ra.bkuang.presentation.ui.base.BaseFragment
import com.ra.bkuang.presentation.ui.customview.dialog.CalendarDialog
import com.ra.bkuang.presentation.ui.customview.dialog.CautionDeleteDialog
import com.ra.bkuang.presentation.ui.customview.dialog.CautionDeleteDialog.Companion.MSG_CAUTION_DIALOG
import com.ra.bkuang.databinding.FragmentDebtBinding
import com.ra.bkuang.domain.model.HutangModel
import com.ra.bkuang.presentation.viewmodel.DebtViewModel
import com.ra.bkuang.presentation.util.AlarmCategory
import com.ra.bkuang.presentation.util.AlarmReceiver
import com.ra.bkuang.presentation.util.AlarmReceiver.Companion.ALARM_CATEGORY
import com.ra.bkuang.presentation.util.ActionType
import com.ra.bkuang.presentation.util.Constants.DATE_PATTERN
import com.ra.bkuang.presentation.util.Constants.LOCALE_ID
import com.ra.bkuang.presentation.util.Extension.cancelAlarm
import com.ra.bkuang.presentation.util.Extension.setExactAndAllowWhileIdleAlarm
import com.ra.bkuang.presentation.util.Extension.showShortToast
import com.ra.bkuang.domain.util.Resource
import com.ra.bkuang.domain.util.ResourceState
import com.ra.bkuang.presentation.ui.features.debt.adapter.DebtAdapter
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
                  ResourceState.LOADING -> {}
                  ResourceState.SUCCESS -> {
                    showShortToast(requireContext().resources.getString(R.string.msg_success))
                    refresh()
                    cautionDeleteDialog.dismiss()
                  }
                  ResourceState.FAILED -> {}
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

        val intent = Intent(requireContext(), AlarmReceiver::class.java).apply {
          putExtra(ALARM_CATEGORY, AlarmCategory.DEBT.name)
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
                    ResourceState.LOADING -> {}
                    ResourceState.SUCCESS -> {
                      requireContext().setExactAndAllowWhileIdleAlarm(calendar, pendingIntent)
                      debtAdapter.notifyItemChanged(adapterPosition)
                      showShortToast(requireContext().getString(R.string.msg_success))
                    }
                    ResourceState.FAILED -> {}
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
                    ResourceState.LOADING -> {}
                    ResourceState.SUCCESS -> {
                      requireContext().cancelAlarm(pendingIntent)
                      debtAdapter.notifyItemChanged(adapterPosition)
                    }
                    ResourceState.FAILED -> {}
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