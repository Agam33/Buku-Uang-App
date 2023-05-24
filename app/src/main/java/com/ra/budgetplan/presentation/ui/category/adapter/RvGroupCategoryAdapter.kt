package com.ra.budgetplan.presentation.ui.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ra.budgetplan.customview.spinner.SpinnerItemOptions
import com.ra.budgetplan.databinding.GroupRvCategoriesBinding
import com.ra.budgetplan.domain.entity.TipeKategori
import com.ra.budgetplan.domain.model.KategoriModel

class RvGroupCategoryAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  var mapCategory: HashMap<TipeKategori, List<KategoriModel>>? = null
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
      val typeValue = TipeKategori.values()
      catHolder.bind(typeValue[position], it[typeValue[position]] ?: mutableListOf())
    }
  }
}