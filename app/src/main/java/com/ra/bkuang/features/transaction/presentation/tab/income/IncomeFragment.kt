package com.ra.bkuang.features.transaction.presentation.tab.income

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseFragment
import com.ra.bkuang.common.util.Extension.hide
import com.ra.bkuang.databinding.FragmentIncomeBinding
import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.features.transaction.domain.model.TransactionGroup
import com.ra.bkuang.features.transaction.presentation.adapter.IncomeRvAdapter
import com.ra.bkuang.features.transaction.presentation.adapter.OnDayItemClickListener
import com.ra.bkuang.features.transaction.presentation.component.DetailTransactionDialog
import com.ra.bkuang.features.transaction.presentation.viewmodel.TransactionViewModel
import com.ra.bkuang.features.transaction.utils.OnTransactionDeleteListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IncomeFragment : BaseFragment<FragmentIncomeBinding>(R.layout.fragment_income),
  OnDayItemClickListener,
  OnTransactionDeleteListener<TransactionDetail> {

  private val viewModel: TransactionViewModel by activityViewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    observer()
  }

  private fun observer() {
    lifecycleScope.launch {
      viewModel.uiState.collect { uiState ->
        with(uiState.incomeUiState) {
          updateAdapter(incomeList)
        }
      }
    }
  }

  private fun updateAdapter(items: List<DetailPendapatan>) {
    if(items.isEmpty()) {
      binding?.rvIncome?.hide(true)
      binding?.emptyLayout?.state = false
      return
    }


    val monthly = TransactionGroup<String, ArrayList<DetailPendapatan>>()
    for (item in items) {
      val updatedAt = item.pendapatan.updatedAt
      val key = updatedAt.toLocalDate().toString()
      monthly.addIf(key, ArrayList())?.add(item)
    }
    val adp = IncomeRvAdapter(monthly)
    adp.onDayItemClickListener = this@IncomeFragment
    binding?.rvIncome?.apply {
      adapter = adp
      layoutManager = LinearLayoutManager(requireContext())
      setHasFixedSize(true)
    }

    binding?.emptyLayout?.state = true
    binding?.rvIncome?.hide(false)
  }

  override fun onDeleteItem(item: TransactionDetail) {
    viewModel.deleteIncomeById(item.uuid)
  }

  override fun onClickDayItem(dayItem: TransactionDetail) {
    val detailTransactionDialog = DetailTransactionDialog()
    detailTransactionDialog.arguments = Bundle().apply {
      putParcelable(DetailTransactionDialog.EXTRA_DETAIL_TRANSACTION, dayItem)
    }
    detailTransactionDialog.onTransactionDeleteListener = this@IncomeFragment
    detailTransactionDialog.show(parentFragmentManager, "income-detail")
  }
}