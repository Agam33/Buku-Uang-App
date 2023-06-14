package com.ra.budgetplan.presentation.ui.transaction.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.budgetplan.databinding.FragmentIncomeBinding
import com.ra.budgetplan.presentation.ui.transaction.adapter.IncomeRvAdapter
import com.ra.budgetplan.presentation.viewmodel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class IncomeFragment : Fragment() {

  private val sharedViewModel: TransactionViewModel by activityViewModels()

  private var _binding: FragmentIncomeBinding? = null
  private val binding get() = _binding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    _binding = FragmentIncomeBinding.inflate(inflater, container, false)
    observer()
    return binding?.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupList()
  }

  private fun setupList() {
    sharedViewModel.listPendapatan.observe(viewLifecycleOwner) {
      binding?.rvIncome?.apply {
        adapter = IncomeRvAdapter(it)
        layoutManager = LinearLayoutManager(requireContext())
        setHasFixedSize(true)
      }
    }
    sharedViewModel.emptyIncomeLayoutState.observe(viewLifecycleOwner) {
      binding?.emptyLayout?.state = it
    }
  }

  private fun observer() {
    _binding?.lifecycleOwner = viewLifecycleOwner
    _binding?.vm = sharedViewModel
  }
}