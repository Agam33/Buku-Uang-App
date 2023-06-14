package com.ra.budgetplan.presentation.ui.transaction.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.budgetplan.databinding.FragmentTransferBinding
import com.ra.budgetplan.presentation.ui.transaction.adapter.TransferRvAdapter
import com.ra.budgetplan.presentation.viewmodel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TransferFragment : Fragment() {

  private val sharedViewModel: TransactionViewModel by activityViewModels()

  private var _binding: FragmentTransferBinding? = null
  private val binding get() =  _binding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    _binding = FragmentTransferBinding.inflate(inflater, container, false)
    observer()
    return binding?.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupList()
  }

  private fun setupList() {
    sharedViewModel.listTransfer.observe(viewLifecycleOwner) {
      binding?.rvTransfer?.apply {
        adapter = TransferRvAdapter(it)
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(requireContext())
      }
    }
    sharedViewModel.emptyTransferLayoutState.observe(viewLifecycleOwner) {
      binding?.emptyLayout?.state = it
    }
  }

  private fun observer() {
    _binding?.lifecycleOwner = viewLifecycleOwner
    _binding?.vm = sharedViewModel
  }

  override fun onDestroy() {
    _binding = null
    super.onDestroy()
  }
}