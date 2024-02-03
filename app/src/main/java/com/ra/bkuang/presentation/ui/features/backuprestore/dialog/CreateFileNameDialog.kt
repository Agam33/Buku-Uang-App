package com.ra.bkuang.presentation.ui.features.backuprestore.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.ra.bkuang.R

class CreateFileNameDialog: DialogFragment() {

  var onSaveListener: OnSaveListener? = null

  private lateinit var edtName: TextInputEditText
  private lateinit var btnSave: MaterialButton
  private lateinit var btnCancel: MaterialButton

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val inflater = activity?.layoutInflater
    val materialDialog = MaterialAlertDialogBuilder(requireContext())
    val view = inflater?.inflate(R.layout.create_file_name_dialog, null)

    materialDialog.setView(view)

    view?.let { v ->
      edtName = v.findViewById(R.id.edt_input_name)
      btnSave = v.findViewById(R.id.btn_save)
      btnCancel = v.findViewById(R.id.btn_cancel)

      btnSave.setOnClickListener {
        validateInput()

      }

      btnCancel.setOnClickListener {
        dismiss()
      }
    }

    return materialDialog.create()
  }

  private fun validateInput() {
    val fileName = edtName.text.toString()
    if(fileName.isEmpty()) return
    onSaveListener?.onSaveInput(fileName)
    dismiss()
  }

  interface OnSaveListener {
    fun onSaveInput(name: String)
  }

  companion object {
    fun newInstance() = CreateFileNameDialog()
    const val TAG = "CreateFileNameDialog"
  }
}