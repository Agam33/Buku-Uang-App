package com.ra.bkuang.features.transaction.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseFragment
import com.ra.bkuang.common.util.Extension.showShortToast
import com.ra.bkuang.common.util.OnDeleteItemListener
import com.ra.bkuang.common.util.OnItemChangedListener
import com.ra.bkuang.common.util.ResultState
import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.databinding.FragmentExpenseBinding
import com.ra.bkuang.features.transaction.data.entity.DetailPengeluaran
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.features.transaction.domain.model.TransactionGroup
import com.ra.bkuang.features.transaction.presentation.TransactionViewModel
import com.ra.bkuang.features.transaction.presentation.adapter.ExpenseRvAdapter
import com.ra.bkuang.features.transaction.presentation.adapter.OnDayItemClickListener
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
        is ResultState.Success -> {
          updateAdapter(it.data ?: return@observe)
        }
        is ResultState.Empty -> {
          sharedViewModel.setStateExpenseListUi(rvState = true, emptyState = false)
        }
        is ResultState.Loading -> {}
        is ResultState.Error -> {}
      }
    }

    sharedViewModel.emptyExpenseLayoutState.observe(viewLifecycleOwner) {
      binding?.emptyLayout?.state = it
    }
  }

  private fun updateAdapter(items: List<DetailPengeluaran>) {
    sharedViewModel.setStateExpenseListUi(rvState = false, emptyState = true)

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
      resourceStateExpense(sharedViewModel.deletePengeluaranById(item.uuid))
    }
    onItemChangedListener?.onItemChanged()
  }

  private fun resourceStateExpense(r: ResourceState) {
    when(r) {
      ResourceState.SUCCESS -> {
        showShortToast(getString(R.string.msg_success))
      }
      ResourceState.FAILED -> {
        showShortToast(getString(R.string.msg_failed))
      }
      ResourceState.LOADING -> {}
    }
  }

  override fun onClickDayItem(dayItem: TransactionDetail) {
    sharedViewModel.setDetailTransaction(dayItem)
    val detailTransactionDialog = DetailTransactionDialog()
    detailTransactionDialog.onDeleteItemListener = this@ExpenseFragment
    detailTransactionDialog.show(parentFragmentManager, "expense-detail")
  }
}