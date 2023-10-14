package com.ra.bkuang.customview.dialog.date

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ra.bkuang.R
import com.ra.bkuang.data.local.preferences.UserSettingPref
import com.ra.bkuang.presentation.ui.transaction.adapter.DateViewType
import com.ra.bkuang.presentation.ui.transaction.adapter.getDateViewType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DateViewTypeDialog: DialogFragment() {

  @Inject
  lateinit var userSettingPref: UserSettingPref

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val inflater = activity?.layoutInflater
    val materialDialog = MaterialAlertDialogBuilder(requireContext(), R.style.Widget_App_Dialog_Rounded)
    val view = inflater?.inflate(R.layout.dialog_date_view_type, null)

    materialDialog.setView(view)

    view?.let {
      setupOption(view)
    }

    return materialDialog.create()
  }

  private fun setupOption(v: View) {
    val radioGroup = v.findViewById<RadioGroup>(R.id.radio_group)

    lifecycleScope.launch {
      val type = userSettingPref.getDateViewType().first()
      when(getDateViewType(type)) {
        DateViewType.DAILY -> radioGroup.check(R.id.rb_daily)
        else -> radioGroup.check(R.id.rb_monthly)
      }
    }

    radioGroup.setOnCheckedChangeListener { _, checkedId ->
      when(checkedId) {
        R.id.rb_daily -> {
          lifecycleScope.launch {
            userSettingPref.saveDateViewType(DateViewType.DAILY)
          }
        }
        R.id.rb_monthly -> {
          lifecycleScope.launch {
            userSettingPref.saveDateViewType(DateViewType.MONTHLY)
          }
        }
      }
    }
  }
}