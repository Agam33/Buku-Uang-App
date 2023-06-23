package com.ra.budgetplan.presentation.ui.transaction.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.budgetplan.databinding.FragmentExpenseBinding
import com.ra.budgetplan.domain.entity.DetailPengeluaran
import com.ra.budgetplan.presentation.ui.transaction.adapter.ExpenseRvAdapter
import com.ra.budgetplan.presentation.viewmodel.TransactionViewModel
import com.ra.budgetplan.util.Resource
import com.ra.budgetplan.util.RvGroup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpenseFragment : Fragment() {

  private var _binding: FragmentExpenseBinding? = null
  private val binding get() = _binding

  private val sharedViewModel: TransactionViewModel by activityViewModels()

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
      val updatedAt = data.pengeluaran.createdAt
      val key = updatedAt.toLocalDate().toString()
      monthly.addIf(key, ArrayList())?.add(data)
    }
    val adp = ExpenseRvAdapter(monthly)
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
}