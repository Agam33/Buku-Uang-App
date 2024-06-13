package com.ra.bkuang.features.transaction.presentation.tab.expense

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseFragment
import com.ra.bkuang.common.util.Extension.hide
import com.ra.bkuang.common.util.OnDeleteItemListener
import com.ra.bkuang.common.util.OnItemChangedListener
import com.ra.bkuang.databinding.FragmentExpenseBinding
import com.ra.bkuang.features.transaction.data.entity.DetailPengeluaran
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.features.transaction.domain.model.TransactionGroup
import com.ra.bkuang.features.transaction.presentation.TransactionFragment
import com.ra.bkuang.features.transaction.presentation.adapter.ExpenseRvAdapter
import com.ra.bkuang.features.transaction.presentation.adapter.OnDayItemClickListener
import com.ra.bkuang.features.transaction.presentation.component.DetailTransactionDialog
import com.ra.bkuang.features.transaction.presentation.tab.expense.viewmodel.ExpenseFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime

@AndroidEntryPoint
class ExpenseFragment : BaseFragment<FragmentExpenseBinding>(R.layout.fragment_expense),
  OnDayItemClickListener,
  OnDeleteItemListener<TransactionDetail>,
  TransactionFragment.OnDateChangedListener {

  private val viewModel: ExpenseFragmentViewModel by viewModels()

  var onItemChangedListener: OnItemChangedListener? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    (requireParentFragment() as TransactionFragment).onDateChangedListener = this@ExpenseFragment
    observer()
  }

  private fun updateAdapter(items: List<DetailPengeluaran>) {
    if(items.isEmpty()) {
      binding?.rvExpense?.hide(true)
      binding?.emptyLayout?.state = false
      return
    }

    binding?.rvExpense?.hide(false)
    binding?.emptyLayout?.state = true

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
  }

  private fun observer() {
    lifecycleScope.launch {
      viewModel.uiState.collect { uiState ->
        updateAdapter(uiState.expenseList)

        uiState.isSuccessful?.let {
          if(it) {
            onItemChangedListener?.onItemChanged()
          }
        }
      }
    }
  }

  private fun setupExpenseDate(currDate: Pair<LocalDateTime, LocalDateTime>) {
    viewModel.getExpenseByDate(currDate.first, currDate.second)
  }

  override fun onDeleteItem(item: TransactionDetail) {
    viewModel.deleteExpenseById(item.uuid)
  }

  override fun onClickDayItem(dayItem: TransactionDetail) {
    val detailTransactionDialog = DetailTransactionDialog()
    detailTransactionDialog.arguments = Bundle().apply {
      putParcelable(DetailTransactionDialog.EXTRA_DETAIL_TRANSACTION, dayItem)
    }
    detailTransactionDialog.onDeleteItemListener = this@ExpenseFragment
    detailTransactionDialog.show(parentFragmentManager, "expense-detail")
  }

  override fun onDateChanged(currDate: Pair<LocalDateTime, LocalDateTime>) {
    Timber.tag("onDateChanged-Expense Fragment").d("$currDate")
    setupExpenseDate(currDate)
  }
}