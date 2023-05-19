package com.ra.budgetplan.customview.dialog.icon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ra.budgetplan.R

class IconAdapter : ListAdapter<Icon, IconAdapter.IconViewHolder>(diffUtil) {

  var onClickItemCallBack: OnClickItemCallBack? = null

  interface OnClickItemCallBack {
    fun getIcon(icon: Icon)
  }

  inner class IconViewHolder(
    view: View
  ): RecyclerView.ViewHolder(view) {
    private val ivIcon: ImageView

    init {
      ivIcon = view.findViewById(R.id.iv_icon)
    }

    fun bind(icon: Icon) {
      ivIcon.setImageResource(icon.icon)
      ivIcon.setOnClickListener { onClickItemCallBack?.getIcon(icon) }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
    return IconViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.item_rv_icons, parent, false)
    )
  }

  override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  companion object {
    private val diffUtil = object: DiffUtil.ItemCallback<Icon>() {

      override fun areItemsTheSame(oldItem: Icon, newItem: Icon): Boolean {
        return oldItem == newItem
      }

      override fun areContentsTheSame(oldItem: Icon, newItem: Icon): Boolean {
        return oldItem.icon == newItem.icon
      }
    }
  }
}