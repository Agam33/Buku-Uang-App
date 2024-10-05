package com.ra.bkuang.features.debt.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ra.bkuang.databinding.ItemRvDebtBinding
import com.ra.bkuang.features.debt.data.model.HutangModel
import com.ra.bkuang.common.util.Extension.toFormatRupiah

class DebtAdapter: ListAdapter<HutangModel, DebtAdapter.MViewHolder>(DIFF) {

  interface OnItemClickListener {
    fun setOnItemClickCallback(model: HutangModel)
  }

  var setItemClickListener: OnItemClickListener? = null

  inner class MViewHolder(
    private val binding: ItemRvDebtBinding
  ): RecyclerView.ViewHolder(binding.root) {

    fun bind(model: HutangModel) {
      binding.run {
        tvTitleDebt.text = model.nama
        tvCurrentMoney.text = model.totalPengeluaran.toFormatRupiah()
        tvGoalMoney.text = model.maxCicilan.toFormatRupiah()

        binding.root.setOnClickListener {
          setItemClickListener?.setOnItemClickCallback(model)
        }

        binding.imgbDetail.setOnClickListener {
          setItemClickListener?.setOnItemClickCallback(model)
        }
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
    return MViewHolder(
      ItemRvDebtBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
  }

  override fun onBindViewHolder(holder: MViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  companion object {
    private val DIFF = object : DiffUtil.ItemCallback<HutangModel>() {
      override fun areItemsTheSame(oldItem: HutangModel, newItem: HutangModel): Boolean {
        return oldItem == newItem
      }

      override fun areContentsTheSame(oldItem: HutangModel, newItem: HutangModel): Boolean {
        return oldItem.uuid == newItem.uuid
      }
    }
  }
}