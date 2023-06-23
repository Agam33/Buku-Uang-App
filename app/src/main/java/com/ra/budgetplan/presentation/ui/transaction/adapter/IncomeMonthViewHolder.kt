package com.ra.budgetplan.presentation.ui.transaction.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ra.budgetplan.databinding.ItemRvIncomeDaysBinding
import com.ra.budgetplan.databinding.ItemRvIncomeMonthBinding
import com.ra.budgetplan.domain.entity.DetailPendapatan

class IncomeMonthViewHolder(
  private val binding: ItemRvIncomeMonthBinding
): RecyclerView.ViewHolder(binding.root) {

  fun bind(date: String, list: List<DetailPendapatan>) {
    val mAdapter = ItemMonthAdapter()
    mAdapter.submitList(list)
    binding.tvTitleRvDays.text = date
    binding.rvIncomeDays.apply {
      adapter = mAdapter
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(
        binding.root.context,
        LinearLayoutManager.VERTICAL,
        false)
    }
  }

  inner class ItemMonthAdapter
    : ListAdapter<DetailPendapatan, IncomeDayViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeDayViewHolder {
      return IncomeDayViewHolder(
        ItemRvIncomeDaysBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
    }

    override fun onBindViewHolder(holder: IncomeDayViewHolder, position: Int) {
      holder.bind(getItem(position))
    }
  }

  companion object {
    private val DIFF_UTIL = object : DiffUtil.ItemCallback<DetailPendapatan>() {
      override fun areItemsTheSame(
        oldItem: DetailPendapatan,
        newItem: DetailPendapatan
      ): Boolean {
        return oldItem == newItem
      }

      override fun areContentsTheSame(
        oldItem: DetailPendapatan,
        newItem: DetailPendapatan
      ): Boolean {
        return oldItem.pendapatan.uuid == newItem.pendapatan.uuid
      }
    }
  }
}