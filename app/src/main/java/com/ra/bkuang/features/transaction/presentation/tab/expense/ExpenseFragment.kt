package com.ra.bkuang.features.transaction.presentation.tab.expense

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseFragment
import com.ra.bkuang.common.util.Extension.hide
import com.ra.bkuang.databinding.FragmentExpenseBinding
import com.ra.bkuang.core.data.source.local.database.entity.DetailPengeluaran
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.features.transaction.domain.model.TransactionGroup
import com.ra.bkuang.features.transaction.presentation.adapter.ExpenseRvAdapter
import com.ra.bkuang.features.transaction.presentation.adapter.OnDayItemClickListener
import com.ra.bkuang.features.transaction.presentation.component.DetailTransactionDialog
import com.ra.bkuang.features.transaction.presentation.viewmodel.TransactionViewModel
import com.ra.bkuang.features.transaction.utils.TransactionDeleteListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExpenseFragment : BaseFragment<FragmentExpenseBinding>(R.layout.fragment_expense),
  OnDayItemClickListener,
  TransactionDeleteListener<TransactionDetail> {

  private val viewModel: TransactionViewModel by activityViewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    observer()
  }

  private fun updateAdapter(items: List<DetailPengeluaran>) {
    if(items.isEmpty()) {
      binding?.rvExpense?.hide(true)
      binding?.emptyLayout?.state = false
      return
    }

    val monthly = TransactionGroup<String, ArrayList<DetailPengeluaran>>()
    for (item in items) {
      val updatedAt = item.pengeluaran.updatedAt
      val key = updatedAt.toLocalDate().toString()
      monthly.addIf(key, ArrayList())?.add(item)
    }
    val adp = ExpenseRvAdapter(monthly)
    adp.onDayItemClickListener = this@ExpenseFragment
    binding?.rvExpense?.apply {
      adapter = adp
      layoutManager = LinearLayoutManager(requireContext())
      setHasFixedSize(true)
    }


    binding?.rvExpense?.hide(false)
    binding?.emptyLayout?.state = true
  }

  private fun observer() {
    lifecycleScope.launch {
      viewModel.uiState.collect { uiState ->
        with(uiState.expenseUiState) {
          updateAdapter(expenseList)
        }
      }
    }
  }

  override fun onDeleteItem(item: TransactionDetail) {
    viewModel.deleteExpenseById(item.uuid)
  }

  override fun onClickDayItem(dayItem: TransactionDetail) {
    val detailTransactionDialog = DetailTransactionDialog()
    detailTransactionDialog.arguments = Bundle().apply {
      putParcelable(DetailTransactionDialog.EXTRA_DETAIL_TRANSACTION, dayItem)
    }
    detailTransactionDialog.transactionDeleteListener = this@ExpenseFragment
    detailTransactionDialog.show(parentFragmentManager, "expense-detail")
  }
}