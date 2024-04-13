package com.ra.bkuang.features.account.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ra.bkuang.common.view.spinner.SpinnerItemOptions
import com.ra.bkuang.common.view.spinner.SpinnerOptionAdapter
import com.ra.bkuang.databinding.ItemRvAccountsBinding
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.common.util.Extension.toFormatRupiah

class RvAccountAdapter: ListAdapter<AkunModel, RvAccountAdapter.MViewHolder>(diff) {

  interface OnOptionAccountClickCallBack {
    fun option(options: SpinnerItemOptions, akun: AkunModel)
  }

  var onOptionAccountClickCallBack: OnOptionAccountClickCallBack? = null

  inner class MViewHolder(
    private val binding: ItemRvAccountsBinding
  ): RecyclerView.ViewHolder(binding.root) {

    fun bind(akun: AkunModel) {
      binding.tvAccount.text = akun.nama
      binding.tvMoney.text = akun.total.toFormatRupiah()

      binding.spOptionAccount.adapter = SpinnerOptionAdapter(binding.root.context)
      binding.spOptionAccount.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {
          if(position > 0) {
            val selectedItem = adapter?.getItemAtPosition(position) as SpinnerItemOptions
            onOptionAccountClickCallBack?.option(selectedItem, akun)
            binding.spOptionAccount.setSelection(0)
          }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
    return MViewHolder(
      ItemRvAccountsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
  }

  override fun onBindViewHolder(holder: MViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  companion object {
    private val diff = object : DiffUtil.ItemCallback<AkunModel>() {
      override fun areItemsTheSame(oldItem: AkunModel, newItem: AkunModel): Boolean {
        return oldItem == newItem
      }

      override fun areContentsTheSame(oldItem: AkunModel, newItem: AkunModel): Boolean {
        return oldItem.uuid == newItem.uuid
      }
    }
  }
}