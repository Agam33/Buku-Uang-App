package com.ra.bkuang.presentation.ui.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ra.bkuang.databinding.ItemRvIcons2Binding
import com.ra.bkuang.domain.model.IconModel

class RvIconCategoryAdapter: ListAdapter<IconModel, RvIconCategoryAdapter.MViewHolder>(diff) {

  private var lastClickedPosition = 0

  var onItemSelectedListener: OnItemSelectedListener? = null

  interface OnItemSelectedListener {
    fun onItemSelected(position: Int, iconModel: IconModel)
  }

  inner class MViewHolder(
    private val binding: ItemRvIcons2Binding
  ): RecyclerView.ViewHolder(binding.root) {

    fun bind(iconModel: IconModel) {
      binding.ivIcon.setImageResource(iconModel.icon)
      onItemSelectedListener?.onItemSelected(lastClickedPosition, getItem(lastClickedPosition))
      binding.root.setOnClickListener {
        setItemStatus(adapterPosition)
      }
    }

    private fun setItemStatus(position: Int) {
      val prevPosition = lastClickedPosition
      lastClickedPosition = position
      notifyItemChanged(prevPosition)
      notifyItemChanged(position)
    }

    fun setActivated(active: Boolean) {
      binding.vSelectedBackground.isActivated = active
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
   return MViewHolder(
     ItemRvIcons2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
   )
  }

  override fun onBindViewHolder(holder: MViewHolder, position: Int) {
    holder.bind(getItem(position))
    holder.setActivated(lastClickedPosition == position)
  }

  companion object {
    private val diff = object: DiffUtil.ItemCallback<IconModel>() {
      override fun areItemsTheSame(oldItem: IconModel, newItem: IconModel): Boolean {
        return oldItem == newItem
      }

      override fun areContentsTheSame(oldItem: IconModel, newItem: IconModel): Boolean {
        return oldItem.icon == newItem.icon
      }
    }
  }
}