package com.ra.bkuang.features.transaction.presentation.tab.transfer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseFragment
import com.ra.bkuang.common.util.Extension.hide
import com.ra.bkuang.common.util.OnDeleteItemListener
import com.ra.bkuang.common.util.OnItemChangedListener
import com.ra.bkuang.databinding.FragmentTransferBinding
import com.ra.bkuang.features.transaction.data.entity.DetailTransfer
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.features.transaction.domain.model.TransactionGroup
import com.ra.bkuang.features.transaction.presentation.TransactionFragment
import com.ra.bkuang.features.transaction.presentation.adapter.OnDayItemClickListener
import com.ra.bkuang.features.transaction.presentation.adapter.TransferRvAdapter
import com.ra.bkuang.features.transaction.presentation.component.DetailTransactionDialog
import com.ra.bkuang.features.transaction.presentation.tab.transfer.viewmodel.TransferFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@AndroidEntryPoint
class TransferFragment : BaseFragment<FragmentTransferBinding>(R.layout.fragment_transfer),
  OnDayItemClickListener,
  OnDeleteItemListener<TransactionDetail>,
  TransactionFragment.OnDateChangedListener {

  private val viewModel: TransferFragmentViewModel by viewModels()

  var onItemChangedListener: OnItemChangedListener? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    observer()
  }

  private fun updateAdapter(items: List<DetailTransfer>) {
    if(items.isEmpty()) {
      binding?.rvTransfer?.hide(true)
      binding?.emptyLayout?.state = true
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

  private fun observer() {
   lifecycleScope.launch {
      viewModel.uiState.collect { uiState ->
        updateAdapter(uiState.transferList)

        uiState.isSuccessful?.let {
          if(it) {
            onItemChangedListener?.onItemChanged()
          }
        }
      }
    }
  }

  private fun setupTransferDate(currDate: Pair<LocalDateTime, LocalDateTime>) {
    viewModel.getTransferByDate(currDate.first, currDate.second)
  }

  override fun onDeleteItem(item: TransactionDetail) {
    viewModel.deleteTransferById(item.uuid)
  }

  override fun onClickDayItem(dayItem: TransactionDetail) {
    val detailTransactionDialog = DetailTransactionDialog()
    detailTransactionDialog.onDeleteItemListener = this@TransferFragment
    detailTransactionDialog.show(parentFragmentManager, "transfer-detail")
  }

  override fun onDateChanged(currDate: Pair<LocalDateTime, LocalDateTime>) {
    setupTransferDate(currDate)
  }
}