package com.ra.budgetplan.presentation.ui.transaction.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.budgetplan.databinding.FragmentExpenseBinding
import com.ra.budgetplan.presentation.ui.transaction.adapter.ExpenseRvAdapter
import com.ra.budgetplan.presentation.viewmodel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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
    return binding?.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    Timber.tag("ExpenseFragment").e("ExpenseFragment - onViewCreated()")
    setupList()
  }

  private fun setupList() {
    sharedViewModel.listPengeluaran.observe(viewLifecycleOwner) {
      binding?.rvExpense?.apply {
        adapter =  ExpenseRvAdapter(it)
        layoutManager = LinearLayoutManager(requireContext())
        setHasFixedSize(true)
      }
    }
    sharedViewModel.emptyExpenseLayoutState.observe(viewLifecycleOwner) {
      binding?.emptyLayout?.state = it
    }
  }

  private fun observer() {
    _binding?.vm = sharedViewModel
    _binding?.lifecycleOwner = viewLifecycleOwner
  }
}