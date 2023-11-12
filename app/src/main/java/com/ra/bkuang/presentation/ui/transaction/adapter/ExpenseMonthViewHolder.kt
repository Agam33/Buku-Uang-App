package com.ra.bkuang.presentation.ui.transaction.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ra.bkuang.databinding.ItemRvExpenseDaysBinding
import com.ra.bkuang.databinding.ItemRvExpenseMonthBinding
import com.ra.bkuang.data.local.entity.DetailPengeluaran
import com.ra.bkuang.domain.mapper.toModel

class ExpenseMonthViewHolder(
  private val binding: ItemRvExpenseMonthBinding
): RecyclerView.ViewHolder(binding.root) {

  var onDayItemClickListener: OnDayItemClickListener? = null

  fun bind(date: String, list: List<DetailPengeluaran>) {
    val mAdapter = ItemMonthAdapter()
    mAdapter.submitList(list)
    binding.tvTitleRvDays.text = date
    binding.rvExpenseDays.apply {
      setHasFixedSize(true)
      adapter = mAdapter
      layoutManager = LinearLayoutManager(
        binding.root.context,
        LinearLayoutManager.VERTICAL,
        false)
    }
  }

  inner class ItemMonthAdapter:
    ListAdapter<DetailPengeluaran, ExpenseDayViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseDayViewHolder {
      return ExpenseDayViewHolder(
        ItemRvExpenseDaysBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
    }

    override fun onBindViewHolder(holder: ExpenseDayViewHolder, position: Int) {
      val item = getItem(position)
      holder.bind(item)
      holder.binding.root.setOnClickListener {
        onDayItemClickListener?.onClickDayItem(item.toModel())
      }
    }
  }

  companion object {
    private val diffUtil = object: DiffUtil.ItemCallback<DetailPengeluaran>() {
      override fun areItemsTheSame(
        oldItem: DetailPengeluaran,
        newItem: DetailPengeluaran
      ): Boolean = oldItem.pengeluaran.uuid == newItem.pengeluaran.uuid

      override fun areContentsTheSame(
        oldItem: DetailPengeluaran,
        newItem: DetailPengeluaran
      ): Boolean = oldItem == newItem
    }
  }
}