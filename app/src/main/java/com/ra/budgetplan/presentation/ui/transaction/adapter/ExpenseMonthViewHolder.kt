package com.ra.budgetplan.presentation.ui.transaction.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ra.budgetplan.databinding.ItemRvExpenseDaysBinding
import com.ra.budgetplan.databinding.ItemRvExpenseMonthBinding
import com.ra.budgetplan.domain.entity.DetailPengeluaran

class ExpenseMonthViewHolder(
  private val binding: ItemRvExpenseMonthBinding
): RecyclerView.ViewHolder(binding.root) {

  fun bind(date: String, list: List<DetailPengeluaran>) {
    val mAdapter = ItemMonthAdapter()
    mAdapter.submitList(list)
    binding.tvTitleRvDays.text = date
    binding.rvExpenseDays.apply {
      setHasFixedSize(true)
      adapter = mAdapter
      layoutManager = LinearLayoutManager(binding.root.context)
    }
  }

  inner class ItemMonthAdapter : ListAdapter<DetailPengeluaran, ExpenseDayViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseDayViewHolder {
      return ExpenseDayViewHolder(
        ItemRvExpenseDaysBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
    }

    override fun onBindViewHolder(holder: ExpenseDayViewHolder, position: Int) {
      holder.bind(getItem(position))
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