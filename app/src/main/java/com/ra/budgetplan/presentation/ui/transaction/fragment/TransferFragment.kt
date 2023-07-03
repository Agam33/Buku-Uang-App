package com.ra.budgetplan.presentation.ui.transaction.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.budgetplan.databinding.FragmentTransferBinding
import com.ra.budgetplan.domain.entity.DetailTransfer
import com.ra.budgetplan.presentation.ui.transaction.TransactionDetail
import com.ra.budgetplan.presentation.ui.transaction.adapter.OnDayItemClickListener
import com.ra.budgetplan.presentation.ui.transaction.adapter.TransferRvAdapter
import com.ra.budgetplan.presentation.viewmodel.TransactionViewModel
import com.ra.budgetplan.util.OnDeleteItemListener
import com.ra.budgetplan.util.OnItemChangedListener
import com.ra.budgetplan.util.Resource
import com.ra.budgetplan.util.RvGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransferFragment : Fragment(), OnDayItemClickListener, OnDeleteItemListener<TransactionDetail> {

  private var _binding: FragmentTransferBinding? = null
  private val binding get() =  _binding

  private val sharedViewModel: TransactionViewModel by activityViewModels()

  var onItemChangedListener: OnItemChangedListener? = null

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    _binding = FragmentTransferBinding.inflate(inflater, container, false)
    observer()
    setupList()
    return binding?.root
  }

  private fun setupList() {
    sharedViewModel.currentDate.observe(viewLifecycleOwner) {
      sharedViewModel.getTransferByDate(it.first, it.second)
    }

    sharedViewModel.listTransfer.observe(viewLifecycleOwner) {
      when (it) {
        is Resource.Success -> {
          updateAdapter(it)
        }

        is Resource.Empty -> {
          sharedViewModel.setStateTransferListUi(rvState = true, emptyState = false)
        }

        is Resource.Loading -> {
          sharedViewModel.setStateTransferListUi(rvState = true, emptyState = false)
        }
      }
    }

    sharedViewModel.emptyTransferLayoutState.observe(viewLifecycleOwner) {
      binding?.emptyLayout?.state = it
    }
  }

  private fun updateAdapter(it: Resource<List<DetailTransfer>>) {
    sharedViewModel.setStateTransferListUi(rvState = false, emptyState = true)

    val monthly = RvGroup<String, ArrayList<DetailTransfer>>()
    for (data in it.data ?: ArrayList()) {
      val updatedAt = data.transfer.updatedAt
      val key = updatedAt.toLocalDate().toString()
      monthly.addIf(key, ArrayList())?.add(data)
    }
    val adp = TransferRvAdapter(monthly)
    adp.onDayItemClickListener = this@TransferFragment
    binding?.rvTransfer?.apply {
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
      sharedViewModel.deleteTransferById(item.uuid)
    }
    onItemChangedListener?.onItemChanged()
  }

  override fun onClickDayItem(dayItem: TransactionDetail) {
    sharedViewModel.setDetailTransaction(dayItem)
    val detailTransactionDialog = DetailTransactionDialog()
    detailTransactionDialog.onDeleteItemListener = this@TransferFragment
    detailTransactionDialog.show(parentFragmentManager, "transfer-detail")
  }
}