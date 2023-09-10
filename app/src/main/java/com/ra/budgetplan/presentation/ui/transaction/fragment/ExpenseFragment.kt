package com.ra.budgetplan.presentation.ui.transaction.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.budgetplan.R
import com.ra.budgetplan.base.BaseFragment
import com.ra.budgetplan.databinding.FragmentExpenseBinding
import com.ra.budgetplan.domain.entity.DetailPengeluaran
import com.ra.budgetplan.presentation.ui.transaction.TransactionDetail
import com.ra.budgetplan.presentation.ui.transaction.adapter.ExpenseRvAdapter
import com.ra.budgetplan.presentation.ui.transaction.adapter.OnDayItemClickListener
import com.ra.budgetplan.presentation.viewmodel.TransactionViewModel
import com.ra.budgetplan.util.OnDeleteItemListener
import com.ra.budgetplan.util.OnItemChangedListener
import com.ra.budgetplan.util.Resource
import com.ra.budgetplan.util.RvGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ExpenseFragment : BaseFragment<FragmentExpenseBinding>(R.layout.fragment_expense),
  OnDayItemClickListener, OnDeleteItemListener<TransactionDetail> {

  private val sharedViewModel: TransactionViewModel by activityViewModels()

  var onItemChangedListener: OnItemChangedListener? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    observer()
    setupList()
  }

  private fun setupList() {
    sharedViewModel.currentDate.observe(viewLifecycleOwner) {
      sharedViewModel.getPengeluaranByDate(it.first, it.second)
    }

    sharedViewModel.listPengeluaran.observe(viewLifecycleOwner) {
      when (it) {
        is Resource.Success -> {
          updateAdapter(it)
        }

        is Resource.Empty -> {
          sharedViewModel.setStateExpenseListUi(rvState = true, emptyState = false)
        }

        is Resource.Loading -> {}
      }
    }

    sharedViewModel.emptyExpenseLayoutState.observe(viewLifecycleOwner) {
      binding?.emptyLayout?.state = it
    }
  }

  private fun updateAdapter(it: Resource<List<DetailPengeluaran>>) {
    sharedViewModel.setStateExpenseListUi(rvState = false, emptyState = true)

    val monthly = RvGroup<String, ArrayList<DetailPengeluaran>>()
    for (data in it.data ?: ArrayList()) {
      val updatedAt = data.pengeluaran.updatedAt
      val key = updatedAt.toLocalDate().toString()
      monthly.addIf(key, ArrayList())?.add(data)
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
    binding?.vm = sharedViewModel
    binding?.lifecycleOwner = viewLifecycleOwner
  }

  override fun onResume() {
    super.onResume()
    Timber.tag("Expense Fragment").d("onResume()")
  }

  override fun onStop() {
    super.onStop()
    Timber.tag("Expense Fragment").d("onStop()")
  }

  override fun onDeleteItem(item: TransactionDetail) {
    viewLifecycleOwner.lifecycleScope.launch {
      sharedViewModel.deletePengeluaranById(item.uuid)
    }
    onItemChangedListener?.onItemChanged()
  }

  override fun onClickDayItem(dayItem: TransactionDetail) {
    sharedViewModel.setDetailTransaction(dayItem)
    val detailTransactionDialog = DetailTransactionDialog()
    detailTransactionDialog.onDeleteItemListener = this@ExpenseFragment
    detailTransactionDialog.show(parentFragmentManager, "expense-detail")
  }
}