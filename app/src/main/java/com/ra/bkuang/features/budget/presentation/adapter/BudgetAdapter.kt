package com.ra.bkuang.features.budget.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ra.bkuang.R
import com.ra.bkuang.common.util.Anim.collapsedWidth
import com.ra.bkuang.common.util.Anim.expandedWidth
import com.ra.bkuang.common.util.Extension.isOverBudget
import com.ra.bkuang.common.util.Extension.toFormatRupiah
import com.ra.bkuang.common.util.Extension.toPercent
import com.ra.bkuang.common.util.Extension.toPercentText
import com.ra.bkuang.databinding.ItemRvBudgetPermonthBinding
import com.ra.bkuang.features.budget.data.local.DetailBudget

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
        expandedWidth(binding.optionLayout, 500, 140)
        true
      }

      binding.root.setOnClickListener {
        collapsedWidth(binding.optionLayout, 500, 0)
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
        if(detailBudget.budget.pengeluaran.isOverBudget(detailBudget.budget.maxPengeluaran)
      ) binding.root.context.getColor(R.color.red_40) else binding.root.context.getColor(R.color.indigo_50))
    }
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