package com.ra.budgetplan.presentation.ui.transaction.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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
class ExpenseFragment : Fragment(),
  OnDayItemClickListener, OnDeleteItemListener<TransactionDetail> {

  private var _binding: FragmentExpenseBinding? = null
  private val binding get() = _binding

  private val sharedViewModel: TransactionViewModel by activityViewModels()

  var onItemChangedListener: OnItemChangedListener? = null

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    // Inflate the layout for this fragment
    _binding = FragmentExpenseBinding.inflate(inflater, container, false)
    observer()
    setupList()
    return binding?.root
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
    _binding?.vm = sharedViewModel
    _binding?.lifecycleOwner = viewLifecycleOwner
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