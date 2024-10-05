package com.ra.bkuang.features.debt.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ra.bkuang.databinding.ItemRvDebtRecordBinding
import com.ra.bkuang.features.debt.data.model.DetailPembayaranHutangModel
import com.ra.bkuang.common.util.Constants.DATE_TIME_FORMATTER
import com.ra.bkuang.common.util.Anim.collapsedWidth
import com.ra.bkuang.common.util.Anim.expandedWidth
import com.ra.bkuang.common.util.Extension.toFormatRupiah
import com.ra.bkuang.common.util.Extension.toStringFormat

class DebtRecordAdapter:
  ListAdapter<DetailPembayaranHutangModel, DebtRecordAdapter.MViewHolder>(DIFF) {

  interface OnItemLongClickListener {
    fun onItemDelete(model: DetailPembayaranHutangModel)
    fun onItemUpdate(model: DetailPembayaranHutangModel)
  }

  var onItemLongClickListener: OnItemLongClickListener? = null

  inner class MViewHolder(
    private val binding: ItemRvDebtRecordBinding,
    private var isCollapsed: Boolean = true
  ): RecyclerView.ViewHolder(binding.root) {

    fun bind(model: DetailPembayaranHutangModel) {
      binding.tvAccountName.text = model.akunModel.nama
      binding.tvMoney.text = model.pembayaranHutangModel.jumlah.toFormatRupiah()
      binding.tvUpdatedAt.text = model.pembayaranHutangModel.updatedAt.toStringFormat(DATE_TIME_FORMATTER)

      binding.root.setOnLongClickListener {
        if(isCollapsed) {
          expandedWidth(binding.optionLayout, 500, 140)
        } else {
          collapsedWidth(binding.optionLayout, 500, 0)
        }
        isCollapsed = !isCollapsed
        true
      }

      binding.root.setOnClickListener {
        if(isCollapsed) {
          expandedWidth(binding.optionLayout, 500, 140)
        } else {
          collapsedWidth(binding.optionLayout, 500, 0)
        }
        isCollapsed = !isCollapsed
      }

      binding.ibDelete.setOnClickListener {
        onItemLongClickListener?.onItemDelete(model)
      }

      binding.ibEdit.setOnClickListener {
        onItemLongClickListener?.onItemUpdate(model)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
    return MViewHolder(
      ItemRvDebtRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
  }

  override fun onBindViewHolder(holder: MViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  companion object {
    private val DIFF = object : DiffUtil.ItemCallback<DetailPembayaranHutangModel>() {
      override fun areItemsTheSame(
          oldItem: DetailPembayaranHutangModel,
          newItem: DetailPembayaranHutangModel
      ): Boolean {
        return oldItem == newItem
      }

      override fun areContentsTheSame(
          oldItem: DetailPembayaranHutangModel,
          newItem: DetailPembayaranHutangModel
      ): Boolean {
        return oldItem.hutangModel.uuid == newItem.hutangModel.uuid
      }
    }
  }
}