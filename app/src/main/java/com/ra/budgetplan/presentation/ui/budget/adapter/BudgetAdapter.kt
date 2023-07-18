package com.ra.budgetplan.presentation.ui.budget.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ra.budgetplan.R
import com.ra.budgetplan.databinding.ItemRvBudgetPermonthBinding
import com.ra.budgetplan.domain.entity.DetailBudget
import com.ra.budgetplan.util.toPercentText
import com.ra.budgetplan.util.collapsed
import com.ra.budgetplan.util.expanded
import com.ra.budgetplan.util.toFormatRupiah
import com.ra.budgetplan.util.toPercent

class BudgetAdapter: ListAdapter<DetailBudget, BudgetAdapter.MViewHolder>(DIFF) {

  interface OnItemLongClickListener {
    fun onItemDelete(detailBudget: DetailBudget)
    fun onItemUpdate(detailBudget: DetailBudget)
  }

  var onItemLongClickListener: OnItemLongClickListener? = null

  inner class MViewHolder(
    private val binding: ItemRvBudgetPermonthBinding
  ): RecyclerView.ViewHolder(binding.root) {

    fun bind(detailBudget: DetailBudget) {
      val percent = detailBudget.budget.pengeluaran.toPercent(detailBudget.budget.maxPengeluaran)

      binding.root.setOnLongClickListener {
        expanded(binding.optionLayout, 500, 140)
        true
      }

      binding.root.setOnClickListener {
        collapsed(binding.optionLayout, 500, 0)
      }

      binding.ibDelete.setOnClickListener {
        onItemLongClickListener?.onItemDelete(detailBudget)
      }

      binding.ibEdit.setOnClickListener {
        onItemLongClickListener?.onItemUpdate(detailBudget)
      }

      binding.tvCurrentMoney.text = detailBudget.budget.pengeluaran.toFormatRupiah()
      binding.tvTitleBudget.text = detailBudget.kategoriEntity.nama
      binding.tvGoalMoney.text = detailBudget.budget.maxPengeluaran.toFormatRupiah()
      binding.tvPercent.text = percent.toPercentText()
      binding.goalProgress.progress = percent.toInt()

      binding.goalProgress.setIndicatorColor(
        if(isOverBudget(detailBudget.budget.pengeluaran, detailBudget.budget.maxPengeluaran)
      ) binding.root.context.getColor(R.color.red_400) else binding.root.context.getColor(R.color.indigo_50))
    }

    private fun isOverBudget(current: Int, maxBudget: Int): Boolean =
      current > maxBudget
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
    return MViewHolder(
      ItemRvBudgetPermonthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
  }

  override fun onBindViewHolder(holder: MViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  companion object {
    private val DIFF = object : DiffUtil.ItemCallback<DetailBudget>() {
      override fun areItemsTheSame(oldItem: DetailBudget, newItem: DetailBudget): Boolean {
        return oldItem == newItem
      }

      override fun areContentsTheSame(oldItem: DetailBudget, newItem: DetailBudget): Boolean {
        return oldItem.budget.uuid == newItem.budget.uuid
      }
    }
  }
}