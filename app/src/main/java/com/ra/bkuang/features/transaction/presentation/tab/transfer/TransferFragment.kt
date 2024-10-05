package com.ra.bkuang.features.transaction.presentation.tab.transfer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseFragment
import com.ra.bkuang.common.util.Extension.hide
import com.ra.bkuang.databinding.FragmentTransferBinding
import com.ra.bkuang.core.data.source.local.database.entity.DetailTransfer
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.features.transaction.domain.model.TransactionGroup
import com.ra.bkuang.features.transaction.presentation.adapter.OnDayItemClickListener
import com.ra.bkuang.features.transaction.presentation.adapter.TransferRvAdapter
import com.ra.bkuang.features.transaction.presentation.component.DetailTransactionDialog
import com.ra.bkuang.features.transaction.presentation.viewmodel.TransactionViewModel
import com.ra.bkuang.features.transaction.utils.TransactionDeleteListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransferFragment : BaseFragment<FragmentTransferBinding>(R.layout.fragment_transfer),
  OnDayItemClickListener,
  TransactionDeleteListener<TransactionDetail> {

  private val viewModel: TransactionViewModel by activityViewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    observer()
  }

  private fun observer() {
    lifecycleScope.launch {
      viewModel.uiState.collect { uiState ->
        with(uiState.transferFragmentUiState) {
          updateAdapter(transferList)
        }
      }
    }
  }

  private fun updateAdapter(items: List<DetailTransfer>) {
    if(items.isEmpty()) {
      binding?.rvTransfer?.hide(true)
      binding?.emptyLayout?.state = false
      return
    }

    binding?.rvTransfer?.hide(false)
    binding?.emptyLayout?.state = true

    val monthly = TransactionGroup<String, ArrayList<DetailTransfer>>()
    for (item in items) {
      val updatedAt = item.transfer.updatedAt
      val key = updatedAt.toLocalDate().toString()
      monthly.addIf(key, ArrayList())?.add(item)
    }
    val adp = TransferRvAdapter(monthly)
    adp.onDayItemClickListener = this@TransferFragment
    binding?.rvTransfer?.apply {
      adapter = adp
      layoutManager = LinearLayoutManager(requireContext())
      setHasFixedSize(true)
    }
  }

  override fun onDeleteItem(item: TransactionDetail) {
    viewModel.deleteTransferById(item.uuid)
  }

  override fun onClickDayItem(dayItem: TransactionDetail) {
    val detailTransactionDialog = DetailTransactionDialog()
    detailTransactionDialog.arguments = Bundle().apply {
      putParcelable(DetailTransactionDialog.EXTRA_DETAIL_TRANSACTION, dayItem)
    }
    detailTransactionDialog.transactionDeleteListener = this@TransferFragment
    detailTransactionDialog.show(parentFragmentManager, "transfer-detail")
  }
}