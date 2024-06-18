package com.ra.bkuang.features.transaction.presentation.component

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.divider.MaterialDivider
import com.google.android.material.snackbar.Snackbar
import com.ra.bkuang.R
import com.ra.bkuang.common.util.ActionType
import com.ra.bkuang.common.util.Extension.getParcel
import com.ra.bkuang.common.util.Extension.toFormatRupiah
import com.ra.bkuang.common.util.Extension.toStringFormat
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.features.transaction.presentation.CreateTransactionActivity
import com.ra.bkuang.features.transaction.presentation.TransactionFragment
import com.ra.bkuang.features.transaction.presentation.TransactionType
import com.ra.bkuang.features.transaction.utils.TransactionDeleteListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class DetailTransactionDialog: DialogFragment() {

  var transactionDeleteListener: TransactionDeleteListener<TransactionDetail>? = null

  private lateinit var tvMoney: TextView
  private lateinit var tvCurrDate: TextView
  private lateinit var tvIc1: TextView
  private lateinit var tvIc2: TextView
  private lateinit var tvTransactionType: TextView
  private lateinit var description: TextView
  private lateinit var divider: MaterialDivider
  private lateinit var closeButton: ImageButton
  private lateinit var deleteButton: ImageButton
  private lateinit var editButton: ImageButton

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val inflater = activity?.layoutInflater
    val materialDialog = MaterialAlertDialogBuilder(requireContext(), R.style.Widget_App_Dialog_Rounded)
    val v = inflater?.inflate(R.layout.detail_transaction_dialog, null)

    val transactionDetail = arguments?.getParcel<TransactionDetail>(EXTRA_DETAIL_TRANSACTION)!!

    materialDialog.setView(v)

    v?.let {
      tvMoney = it.findViewById(R.id.tv_money)
      tvCurrDate = it.findViewById(R.id.tv_current_date)
      divider = it.findViewById(R.id.divider)
      tvIc1 = it.findViewById(R.id.tv_ic1)
      tvIc2 = it.findViewById(R.id.tv_ic2)
      tvTransactionType = it.findViewById(R.id.tv_transaction_type)
      description = it.findViewById(R.id.tv_desc)
      closeButton = it.findViewById(R.id.ib_close_dialog)
      deleteButton = it.findViewById(R.id.ib_delete_dialog)
      editButton = it.findViewById(R.id.ib_edit_dialog)

      setupText(transactionDetail)

      setupButton(it, transactionDetail)
    }

    return materialDialog.create()
  }

  private fun setupButton(v: View, transactionDetail: TransactionDetail) {
    closeButton.setOnClickListener {
      onDestroyView()
    }
    deleteButton.setOnClickListener {
      Snackbar.make(
        v,
        requireContext().getString(R.string.msg_delete),
        Snackbar.LENGTH_SHORT
      ).setAction("Ya") {
          transactionDeleteListener?.onDeleteItem(transactionDetail)
          onDestroyView()
        }
        .show()
    }
    editButton.setOnClickListener {
      val i = Intent(requireContext(), CreateTransactionActivity::class.java).apply {
        putExtra(TransactionFragment.EXTRA_TRANSACTION_TYPE, transactionDetail.transactionType.name)
        putExtra(TransactionFragment.EXTRA_TRANSACTION_CREATE_OR_EDIT, ActionType.EDIT.name)
        putExtra(EXTRA_TRANSACTION_ID, transactionDetail.uuid.toString())
      }
      startActivity(i)
      onDestroyView()
    }
  }

  private fun setupText(transactionDetail: TransactionDetail) {
    when(transactionDetail.transactionType) {
      TransactionType.TRANSFER -> {
        tvTransactionType.text = requireContext().getString(R.string.title_transfer)
        divider.dividerColor = requireContext().getColor(R.color.indigo_40)
      }
      TransactionType.EXPENSE -> {
        tvTransactionType.text = requireContext().getString(R.string.title_expense)
        divider.dividerColor = requireContext().getColor(R.color.red_40)
      }
      TransactionType.INCOME -> {
        tvTransactionType.text = requireContext().getString(R.string.title_income)
        divider.dividerColor = requireContext().getColor(R.color.green_40)
      }
    }

    tvCurrDate.text = transactionDetail.updatedAt.toStringFormat("d MMMM, yyyy HH:mm", Locale("id", "id"))
    tvIc1.text = transactionDetail.name1
    tvIc2.text = transactionDetail.name2
    tvMoney.text = transactionDetail.jumlah.toFormatRupiah()
    description.text = transactionDetail.description
  }

  companion object {
    const val EXTRA_TRANSACTION_ID = "transaction-id"
    const val EXTRA_DETAIL_TRANSACTION = "detail-transaction"
  }
}