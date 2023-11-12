package com.ra.bkuang.presentation.ui.transaction.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ra.bkuang.databinding.ItemRvTransferDaysBinding
import com.ra.bkuang.databinding.ItemRvTransferMonthBinding
import com.ra.bkuang.data.local.entity.DetailTransfer
import com.ra.bkuang.domain.mapper.toModel

class TransferMonthViewHolder(
  private val binding: ItemRvTransferMonthBinding
): RecyclerView.ViewHolder(binding.root) {

  var onDayItemClickListener: OnDayItemClickListener? = null

  fun bind(date: String, list: List<DetailTransfer>) {
    val mAdapter = ItemMonthAdapter()
    mAdapter.submitList(list)
    binding.tvTitleRvDays.text = date
    binding.rvTransferDays.apply {
      adapter = mAdapter
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(
        binding.root.context,
        LinearLayoutManager.VERTICAL,
        false)
    }
  }

  inner class ItemMonthAdapter
    : ListAdapter<DetailTransfer, TransferDayViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransferDayViewHolder {
      return TransferDayViewHolder(
        ItemRvTransferDaysBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
    }

    override fun onBindViewHolder(holder: TransferDayViewHolder, position: Int) {
      val item = getItem(position)
      holder.bind(item)
      holder.binding.root.setOnClickListener {
        onDayItemClickListener?.onClickDayItem(item.toModel())
      }
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