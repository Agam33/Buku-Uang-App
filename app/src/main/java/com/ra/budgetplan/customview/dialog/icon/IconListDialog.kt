package com.ra.budgetplan.customview.dialog.icon

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ra.budgetplan.R

class IconListDialog: DialogFragment(), IconAdapter.OnClickItemCallBack {

  private lateinit var rvIcon: RecyclerView

  interface OnClickItemListener {
    fun getIcon(icon: Icon)
  }

  var onClickItemListener: OnClickItemListener? = null
  var category: IconCategory = IconCategory.INCOME

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val builder = AlertDialog.Builder(activity)
    val inflater = activity?.layoutInflater
    val view = inflater?.inflate(R.layout.icon_list_dialog_layout, null)
    builder.setView(view)

    setupWindow()

    view?.let { v ->
      rvIcon = v.findViewById(R.id.rv_icons)
    }

    setupListIcon()

    return builder.create()
  }

  private fun setupWindow() {
    val layoutParams = dialog?.window?.attributes
    layoutParams?.width = WindowManager.LayoutParams.WRAP_CONTENT
    dialog?.window?.attributes = layoutParams
  }

  private fun setupListIcon() {
    val icAdapter = IconAdapter()
    icAdapter.onClickItemCallBack = this@IconListDialog

    val iconList = when(category) {
      IconCategory.ACCOUNT -> IconData.setIconAccount(requireContext())
      IconCategory.EXPENSE -> IconData.setExpensesIcon(requireContext())
      else -> IconData.setIncomeIcon(requireContext())
    }

    icAdapter.submitList(iconList)
    rvIcon.apply {
      adapter = icAdapter
      layoutManager = GridLayoutManager(requireContext(), 3, LinearLayoutManager.HORIZONTAL, false)
      setHasFixedSize(true)
    }
  }

  override fun getIcon(icon: Icon) {
    onClickItemListener?.getIcon(icon)
    dismiss()
  }
}