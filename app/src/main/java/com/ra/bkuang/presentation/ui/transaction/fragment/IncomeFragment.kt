package com.ra.bkuang.presentation.ui.transaction.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.bkuang.R
import com.ra.bkuang.base.BaseFragment
import com.ra.bkuang.databinding.FragmentIncomeBinding
import com.ra.bkuang.data.entity.DetailPendapatan
import com.ra.bkuang.presentation.ui.transaction.TransactionDetail
import com.ra.bkuang.presentation.ui.transaction.adapter.IncomeRvAdapter
import com.ra.bkuang.presentation.ui.transaction.adapter.OnDayItemClickListener
import com.ra.bkuang.presentation.viewmodel.TransactionViewModel
import com.ra.bkuang.util.Extension.showShortToast
import com.ra.bkuang.util.OnDeleteItemListener
import com.ra.bkuang.util.OnItemChangedListener
import com.ra.bkuang.util.Resource
import com.ra.bkuang.util.ResourceState
import com.ra.bkuang.util.RvGroup
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
        is Resource.Success -> {
          updateAdapter(it)
        }

        is Resource.Empty -> {
          sharedViewModel.setStateIncomeListUi(rvState = true, emptyState = false)
        }

        is Resource.Loading -> {}
      }
    }

    sharedViewModel.emptyIncomeLayoutState.observe(viewLifecycleOwner) {
      binding?.emptyLayout?.state = it
    }
  }

  private fun updateAdapter(it: Resource<List<DetailPendapatan>>) {
    sharedViewModel.setStateIncomeListUi(rvState = false, emptyState = true)

    val monthly = RvGroup<String, ArrayList<DetailPendapatan>>()
    for (data in it.data ?: ArrayList()) {
      val updatedAt = data.pendapatan.updatedAt
      val key = updatedAt.toLocalDate().toString()
      monthly.addIf(key, ArrayList())?.add(data)
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