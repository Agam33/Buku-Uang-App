package com.ra.bkuang.features.category.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ra.bkuang.common.view.spinner.SpinnerItemOptions
import com.ra.bkuang.databinding.GroupRvCategoriesBinding
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.transaction.data.entity.TransactionType

class RvGroupCategoryAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  var mapCategory: HashMap<TransactionType, List<KategoriModel>>? = null
  var onOptionCategoryClickCallBack: OnOptionCategoryClickCallBack? = null

  interface OnOptionCategoryClickCallBack {
    fun option(options: SpinnerItemOptions, kategoriModel: KategoriModel)
  }

  override fun getItemCount(): Int = mapCategory?.size ?: 0

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return RvCategoryViewHolder(
      GroupRvCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false),
      onOptionCategoryClickCallBack
    )
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    mapCategory?.let {
      val catHolder = holder as RvCategoryViewHolder
      val typeValue = TransactionType.values()
      catHolder.bind(typeValue[position], it[typeValue[position]] ?: mutableListOf())
    }
  }
}