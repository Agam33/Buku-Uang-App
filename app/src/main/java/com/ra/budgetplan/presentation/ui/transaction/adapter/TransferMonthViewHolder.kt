package com.ra.budgetplan.presentation.ui.transaction.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ra.budgetplan.databinding.ItemRvIncomeMonthBinding
import com.ra.budgetplan.databinding.ItemRvTransferDaysBinding
import com.ra.budgetplan.domain.entity.DetailTransfer

class TransferMonthViewHolder(
  private val binding: ItemRvIncomeMonthBinding
): RecyclerView.ViewHolder(binding.root) {

  fun bind(date: String, list: List<DetailTransfer>) {
    val mAdapter = ItemMonthAdapter()
    mAdapter.submitList(list)
    binding.rvIncomeDays.apply {
      adapter = mAdapter
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(binding.root.context)
    }
    binding.tvTitleRvDays.text = date
  }

  inner class ItemMonthAdapter: ListAdapter<DetailTransfer, TransferDayViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransferDayViewHolder {
      return TransferDayViewHolder(
        ItemRvTransferDaysBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
    }

    override fun onBindViewHolder(holder: TransferDayViewHolder, position: Int) {
      holder.bind(getItem(position))
    }
  }

  companion object {
    private val DIFF_UTIL = object: DiffUtil.ItemCallback<DetailTransfer>() {
      override fun areItemsTheSame(oldItem: DetailTransfer, newItem: DetailTransfer): Boolean {
        return oldItem == newItem
      }

      override fun areContentsTheSame(oldItem: DetailTransfer, newItem: DetailTransfer): Boolean {
        return oldItem.transfer.uuid == newItem.transfer.uuid
      }
    }
  }
}