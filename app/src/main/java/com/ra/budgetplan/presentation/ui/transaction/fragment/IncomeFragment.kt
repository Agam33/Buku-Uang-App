package com.ra.budgetplan.presentation.ui.transaction.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.budgetplan.databinding.FragmentIncomeBinding
import com.ra.budgetplan.domain.entity.DetailPendapatan
import com.ra.budgetplan.presentation.ui.transaction.TransactionDetail
import com.ra.budgetplan.presentation.ui.transaction.adapter.IncomeRvAdapter
import com.ra.budgetplan.presentation.ui.transaction.adapter.OnDayItemClickListener
import com.ra.budgetplan.presentation.viewmodel.TransactionViewModel
import com.ra.budgetplan.util.OnDeleteItemListener
import com.ra.budgetplan.util.OnItemChangedListener
import com.ra.budgetplan.util.Resource
import com.ra.budgetplan.util.RvGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IncomeFragment : Fragment(),
  OnDayItemClickListener,
  OnDeleteItemListener<TransactionDetail> {

  private val sharedViewModel: TransactionViewModel by activityViewModels()

  private var _binding: FragmentIncomeBinding? = null
  private val binding get() = _binding

  var onItemChangedListener: OnItemChangedListener? = null

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    _binding = FragmentIncomeBinding.inflate(inflater, container, false)
    observer()
    setupList()
    return binding?.root
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
    _binding?.vm = sharedViewModel
    _binding?.lifecycleOwner = viewLifecycleOwner
  }

  override fun onDeleteItem(item: TransactionDetail) {
    viewLifecycleOwner.lifecycleScope.launch {
      sharedViewModel.deletePendapatanById(item.uuid)
    }
    onItemChangedListener?.onItemChanged()
  }

  override fun onClickDayItem(dayItem: TransactionDetail) {
    sharedViewModel.setDetailTransaction(dayItem)
    val detailTransactionDialog = DetailTransactionDialog()
    detailTransactionDialog.onDeleteItemListener = this@IncomeFragment
    detailTransactionDialog.show(parentFragmentManager, "income-detail")
  }
}