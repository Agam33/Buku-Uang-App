package com.ra.bkuang.features.analytics.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ra.bkuang.databinding.ItemRvDataAnalyticsBinding
import com.ra.bkuang.features.analytics.data.model.AnalyticModel
import com.ra.bkuang.common.util.Extension.toFormatRupiah
import com.ra.bkuang.common.util.Extension.toPercentText

class AnalyticListAdapter: ListAdapter<AnalyticModel, AnalyticListAdapter.MViewHolder>(DIFF) {

  inner class MViewHolder(
    val binding: ItemRvDataAnalyticsBinding
  ): RecyclerView.ViewHolder(binding.root) {

    fun bind(analyticModel: AnalyticModel) {
      binding.tvTitle.text = analyticModel.name
      binding.progressCircular.progress = analyticModel.percent.toInt()
      binding.tvPercent.text = analyticModel.percent.toPercentText()
      binding.tvCurrentMoney.text = analyticModel.total.toFormatRupiah()
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder =
    MViewHolder(
      ItemRvDataAnalyticsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

  override fun onBindViewHolder(holder: MViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  companion object {
    private val DIFF = object : DiffUtil.ItemCallback<AnalyticModel>() {
      override fun areItemsTheSame(oldItem: AnalyticModel, newItem: AnalyticModel): Boolean {
        return oldItem == newItem
      }

      override fun areContentsTheSame(oldItem: AnalyticModel, newItem: AnalyticModel): Boolean {
        return oldItem.name == newItem.name
      }
    }
  }

}