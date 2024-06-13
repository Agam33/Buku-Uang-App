package com.ra.bkuang.features.transaction.presentation.tab.income

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
import com.ra.bkuang.databinding.FragmentIncomeBinding
import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.features.transaction.domain.model.TransactionGroup
import com.ra.bkuang.features.transaction.presentation.TransactionFragment
import com.ra.bkuang.features.transaction.presentation.adapter.IncomeRvAdapter
import com.ra.bkuang.features.transaction.presentation.adapter.OnDayItemClickListener
import com.ra.bkuang.features.transaction.presentation.component.DetailTransactionDialog
import com.ra.bkuang.features.transaction.presentation.tab.income.viewmodel.IncomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime

@AndroidEntryPoint
class IncomeFragment : BaseFragment<FragmentIncomeBinding>(R.layout.fragment_income),
  OnDayItemClickListener,
  OnDeleteItemListener<TransactionDetail>,
  TransactionFragment.OnDateChangedListener {

  private val viewModel: IncomeFragmentViewModel by viewModels()

  var onItemChangedListener: OnItemChangedListener? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    (requireParentFragment() as TransactionFragment).onDateChangedListener = this@IncomeFragment
    observer()
  }

  private fun observer() {
    lifecycleScope.launch {
      viewModel.uiState.collect { uiState ->
        updateAdapter(uiState.incomeList)

        uiState.isSuccessful?.let {
          if(it) {
            onItemChangedListener?.onItemChanged()
          }
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

    binding?.rvIncome?.hide(false)
    binding?.emptyLayout?.state = true

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
  }

  private fun setupIncomeDate(currDate: Pair<LocalDateTime, LocalDateTime>) {
    viewModel.getIncomeByDate(currDate.first, currDate.second)
  }

  override fun onDeleteItem(item: TransactionDetail) {
    viewModel.deleteIncomeById(item.uuid)
  }

  override fun onClickDayItem(dayItem: TransactionDetail) {
    val detailTransactionDialog = DetailTransactionDialog()
    detailTransactionDialog.arguments = Bundle().apply {
      putParcelable(DetailTransactionDialog.EXTRA_DETAIL_TRANSACTION, dayItem)
    }
    detailTransactionDialog.onDeleteItemListener = this@IncomeFragment
    detailTransactionDialog.show(parentFragmentManager, "income-detail")
  }

  override fun onDateChanged(currDate: Pair<LocalDateTime, LocalDateTime>) {
    Timber.tag("onDateChanged-Income Fragment").d("$currDate")
    setupIncomeDate(currDate)
  }
}