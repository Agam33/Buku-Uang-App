package com.ra.bkuang.features.transaction.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseFragment
import com.ra.bkuang.databinding.FragmentIncomeBinding
import com.ra.bkuang.features.transaction.presentation.TransactionViewModel
import com.ra.bkuang.common.util.Extension.showShortToast
import com.ra.bkuang.common.util.OnDeleteItemListener
import com.ra.bkuang.common.util.OnItemChangedListener
import com.ra.bkuang.common.util.ResultState
import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import com.ra.bkuang.features.transaction.domain.model.TransactionGroup
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.features.transaction.presentation.adapter.IncomeRvAdapter
import com.ra.bkuang.features.transaction.presentation.adapter.OnDayItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IncomeFragment : BaseFragment<FragmentIncomeBinding>(R.layout.fragment_income),
  OnDayItemClickListener,
  OnDeleteItemListener<TransactionDetail> {

  private val sharedViewModel: TransactionViewModel by activityViewModels()

  var onItemChangedListener: OnItemChangedListener? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    observer()
    setupList()
  }

  private fun setupList() {
    sharedViewModel.currentDate.observe(viewLifecycleOwner) {
      sharedViewModel.getPendapatanByDate(it.first, it.second)
    }

    sharedViewModel.incomes.observe(viewLifecycleOwner) {
      when (it) {
        is ResultState.Success -> {
          updateAdapter(it.data ?: return@observe)
        }
        is ResultState.Empty -> {
          sharedViewModel.setStateIncomeListUi(rvState = true, emptyState = false)
        }
        is ResultState.Loading -> {}
        is ResultState.Error -> {}
      }
    }

    sharedViewModel.emptyIncomeLayoutState.observe(viewLifecycleOwner) {
      binding?.emptyLayout?.state = it
    }
  }

  private fun updateAdapter(items: List<DetailPendapatan>) {
    sharedViewModel.setStateIncomeListUi(rvState = false, emptyState = true)

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

  private fun observer() {
    binding?.vm = sharedViewModel
    binding?.lifecycleOwner = viewLifecycleOwner
  }

  override fun onDeleteItem(item: TransactionDetail) {
    viewLifecycleOwner.lifecycleScope.launch {
      resourceStateIncome(sharedViewModel.deletePendapatanById(item.uuid))
    }
    onItemChangedListener?.onItemChanged()
  }

  private fun resourceStateIncome(r: ResourceState) {
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
    detailTransactionDialog.onDeleteItemListener = this@IncomeFragment
    detailTransactionDialog.show(parentFragmentManager, "income-detail")
  }
}