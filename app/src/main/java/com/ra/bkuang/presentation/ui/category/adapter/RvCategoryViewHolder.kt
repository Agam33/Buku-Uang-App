package com.ra.bkuang.presentation.ui.category.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ra.bkuang.custom.spinner.SpinnerItemOptions
import com.ra.bkuang.custom.spinner.SpinnerOptionAdapter
import com.ra.bkuang.databinding.GroupRvCategoriesBinding
import com.ra.bkuang.databinding.ItemRvCategoriesBinding
import com.ra.bkuang.data.local.database.entity.TipeKategori
import com.ra.bkuang.domain.model.KategoriModel
import com.ra.bkuang.util.Extension.firstCharUppercase

class RvCategoryViewHolder(
  private val binding: GroupRvCategoriesBinding,
  private var onOptionCategoryClickCallBack: RvGroupCategoryAdapter.OnOptionCategoryClickCallBack? = null
): RecyclerView.ViewHolder(binding.root) {

  fun bind(type: TipeKategori, list: List<KategoriModel>) {
    val itemCategoryAdapter = ItemCategoryAdapter()
    itemCategoryAdapter.submitList(list)
    binding.tvTitleCategory.text = type.name.firstCharUppercase()
    binding.rvGroupItemCategories.apply {
      adapter = itemCategoryAdapter
      layoutManager = LinearLayoutManager(binding.root.context)
      setHasFixedSize(true)
    }
  }

  inner class ItemCategoryAdapter:
    ListAdapter<KategoriModel, ItemCategoryAdapter.MViewHolder>(kategoriModelDiffUtil) {

    inner class MViewHolder(
      private val binding: ItemRvCategoriesBinding
    ): RecyclerView.ViewHolder(binding.root) {

      fun bind(model: KategoriModel) {
        binding.tvCategory.text = model.nama

        binding.spOptionCategory.adapter = SpinnerOptionAdapter(binding.root.context)
        binding.spOptionCategory.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
          override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, p3: Long) {
            if(position > 0) {
              val selectedItem = adapter?.getItemAtPosition(position) as SpinnerItemOptions
              onOptionCategoryClickCallBack?.option(selectedItem, model)
              binding.spOptionCategory.setSelection(0)
            }
          }

          override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
      return MViewHolder(
        ItemRvCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
      holder.bind(getItem(position))
    }
  }

  companion object {
    private val kategoriModelDiffUtil = object: DiffUtil.ItemCallback<KategoriModel>() {
      override fun areItemsTheSame(oldItem: KategoriModel, newItem: KategoriModel): Boolean {
        return oldItem == newItem
      }

      override fun areContentsTheSame(oldItem: KategoriModel, newItem: KategoriModel): Boolean {
        return oldItem.uuid == newItem.uuid
      }
    }
  }
}