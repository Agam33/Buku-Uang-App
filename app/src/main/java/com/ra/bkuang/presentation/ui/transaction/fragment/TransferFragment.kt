package com.ra.bkuang.presentation.ui.transaction.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.bkuang.R
import com.ra.bkuang.base.BaseFragment
import com.ra.bkuang.databinding.FragmentTransferBinding
import com.ra.bkuang.data.entity.DetailTransfer
import com.ra.bkuang.presentation.ui.transaction.TransactionDetail
import com.ra.bkuang.presentation.ui.transaction.adapter.OnDayItemClickListener
import com.ra.bkuang.presentation.ui.transaction.adapter.TransferRvAdapter
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
class TransferFragment : BaseFragment<FragmentTransferBinding>(R.layout.fragment_transfer), OnDayItemClickListener, OnDeleteItemListener<TransactionDetail> {

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