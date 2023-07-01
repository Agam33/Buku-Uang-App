package com.ra.budgetplan.presentation.ui.transaction.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.divider.MaterialDivider
import com.google.android.material.snackbar.Snackbar
import com.ra.budgetplan.R
import com.ra.budgetplan.presentation.ui.transaction.TransactionDetail
import com.ra.budgetplan.presentation.ui.transaction.TransactionType
import com.ra.budgetplan.presentation.viewmodel.TransactionViewModel
import com.ra.budgetplan.util.OnDeleteItemListener
import com.ra.budgetplan.util.toFormatRupiah
import com.ra.budgetplan.util.toStringFormat
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class DetailTransactionDialog: DialogFragment() {

  var onDeleteItemListener: OnDeleteItemListener<TransactionDetail>? = null

  private lateinit var tvMoney: TextView
  private lateinit var tvCurrDate: TextView
  private lateinit var tvIc1: TextView
  private lateinit var tvIc2: TextView
  private lateinit var tvTransactionType: TextView
  private lateinit var description: TextView
  private lateinit var ivIc1: ImageView
  private lateinit var ivIc2: ImageView
  private lateinit var divider: MaterialDivider
  private lateinit var closeButton: ImageButton
  private lateinit var deleteButton: ImageButton
  private lateinit var editButton: ImageButton

  private val sharedViewModel: TransactionViewModel by activityViewModels()

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val inflater = activity?.layoutInflater
    val materialDialog = MaterialAlertDialogBuilder(requireContext(), R.style.Widget_App_Dialog_Rounded)
    val v = inflater?.inflate(R.layout.detail_transaction_dialog, null)

    materialDialog.setView(v)

    v?.let {
      tvMoney = it.findViewById(R.id.tv_money)
      tvCurrDate = it.findViewById(R.id.tv_current_date)
      divider = it.findViewById(R.id.divider)
      ivIc1 = it.findViewById(R.id.iv_ic1)
      ivIc2 = it.findViewById(R.id.iv_ic2)
      tvIc1 = it.findViewById(R.id.tv_ic1)
      tvIc2 = it.findViewById(R.id.tv_ic2)
      tvTransactionType = it.findViewById(R.id.tv_transaction_type)
      description = it.findViewById(R.id.tv_desc)
      closeButton = it.findViewById(R.id.ib_close_dialog)
      deleteButton = it.findViewById(R.id.ib_delete_dialog)
      editButton = it.findViewById(R.id.ib_edit_dialog)

      sharedViewModel.detailTransaction
        .observe(requireParentFragment().viewLifecycleOwner) { transactionDetail ->
          setupText(transactionDetail)
      }

      setupButton(it)
    }

    return materialDialog.create()
  }

  private fun setupButton(v: View) {
    closeButton.setOnClickListener {
      onDestroyView()
    }
    deleteButton.setOnClickListener {
      Snackbar.make(
        v,
        requireContext().getString(R.string.msg_delete),
        Snackbar.LENGTH_SHORT
      )
        .setAction("Ya") {
          sharedViewModel.detailTransaction
            .observe(requireParentFragment().viewLifecycleOwner) { transactionDetail ->
              onDeleteItemListener?.onDeleteItem(transactionDetail)
            }
          onDestroyView()
        }
        .show()
    }
    editButton.setOnClickListener {

    }
  }

  override fun onDestroyView() {
    sharedViewModel.detailTransaction.removeObservers(requireParentFragment().viewLifecycleOwner)
    super.onDestroyView()
  }

  private fun setupText(transactionDetail: TransactionDetail) {
    when(transactionDetail.transactionType) {
      TransactionType.TRANSFER -> {
        tvTransactionType.text = requireContext().getString(R.string.title_transfer)
        divider.dividerColor = requireContext().getColor(R.color.indigo_40)
      }
      TransactionType.EXPENSE -> {
        tvTransactionType.text = requireContext().getString(R.string.title_expense)
        divider.dividerColor = requireContext().getColor(R.color.red_400)
      }
      TransactionType.INCOME -> {
        tvTransactionType.text = requireContext().getString(R.string.title_income)
        divider.dividerColor = requireContext().getColor(R.color.green_40)
      }
    }

    tvCurrDate.text = transactionDetail.updatedAt.toStringFormat("d MMMM, yyyy HH:mm", Locale("id", "id"))
    ivIc1.setImageResource(transactionDetail.icUrl1)
    ivIc2.setImageResource(transactionDetail.icUrl2)
    tvIc1.text = transactionDetail.name1
    tvIc2.text = transactionDetail.name2
    tvMoney.text = transactionDetail.jumlah.toFormatRupiah()
    description.text = transactionDetail.description
  }
}