package com.ra.bkuang.features.transaction.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseFragment
import com.ra.bkuang.databinding.FragmentTransferBinding
import com.ra.bkuang.features.transaction.presentation.TransactionViewModel
import com.ra.bkuang.common.util.Extension.showShortToast
import com.ra.bkuang.common.util.OnDeleteItemListener
import com.ra.bkuang.common.util.OnItemChangedListener
import com.ra.bkuang.common.util.Resource
import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.transaction.data.entity.DetailTransfer
import com.ra.bkuang.features.transaction.domain.model.TransactionGroup
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.features.transaction.presentation.adapter.OnDayItemClickListener
import com.ra.bkuang.features.transaction.presentation.adapter.TransferRvAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransferFragment : BaseFragment<FragmentTransferBinding>(R.layout.fragment_transfer),
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

    val monthly = TransactionGroup<String, ArrayList<DetailTransfer>>()
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
    binding?.vm = sharedViewModel
    binding?.lifecycleOwner = viewLifecycleOwner
  }

  override fun onDeleteItem(item: TransactionDetail) {
    viewLifecycleOwner.lifecycleScope.launch {
      resourceStateTransfer(sharedViewModel.deleteTransferById(item.uuid))
    }
    onItemChangedListener?.onItemChanged()
  }

  override fun onClickDayItem(dayItem: TransactionDetail) {
    sharedViewModel.setDetailTransaction(dayItem)
    val detailTransactionDialog = DetailTransactionDialog()
    detailTransactionDialog.onDeleteItemListener = this@TransferFragment
    detailTransactionDialog.show(parentFragmentManager, "transfer-detail")
  }

  private fun resourceStateTransfer(r: ResourceState) {
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
}