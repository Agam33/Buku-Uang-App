package com.ra.bkuang.customview.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ra.bkuang.R

class CautionDeleteDialog: DialogFragment() {

  interface OnOptionItemClick {
    fun onDelete()
    fun onCancel()
  }

  var onOptionItemClick: OnOptionItemClick? = null

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val inflater = requireParentFragment().layoutInflater
    val materialDialog = MaterialAlertDialogBuilder(requireContext(), R.style.Widget_App_Dialog_Rounded)
    val view = inflater.inflate(R.layout.caution_delete_dialog, null)

    materialDialog.setView(view)

    setupOption(view)

    return materialDialog.create()
  }

  private fun setupOption(v: View) {
    val btnYes = v.findViewById<Button>(R.id.btn_yes)
    val btnCancel = v.findViewById<Button>(R.id.btn_no)
    val tvMsg = v.findViewById<TextView>(R.id.tv_msg_delete)

    val msg = arguments?.getString(MSG_CAUTION_DIALOG,
      requireContext().resources.getString(R.string.msg_delete_dialog))

    tvMsg.text = msg

    btnYes.setOnClickListener { onOptionItemClick?.onDelete() }
    btnCancel.setOnClickListener { onOptionItemClick?.onCancel() }
  }

  companion object {
    const val MSG_CAUTION_DIALOG = "msg-caution-dialog"
  }
}