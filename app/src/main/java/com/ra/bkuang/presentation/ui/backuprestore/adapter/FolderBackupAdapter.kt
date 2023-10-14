package com.ra.bkuang.presentation.ui.backuprestore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ra.bkuang.databinding.ItemRvFolderBackupBinding
import java.io.File

class FolderBackupAdapter(
  private val files: List<File>
): RecyclerView.Adapter<FolderBackupAdapter.MViewHolder>() {

  private var lastClickedPosition = 0

  var onItemClickListener: OnItemClickListener? = null

  interface OnItemClickListener {
    fun onItemSelected(file: File)
  }

  inner class MViewHolder(
    private val binding: ItemRvFolderBackupBinding
  ): RecyclerView.ViewHolder(binding.root) {

    fun bind(file: File) {
      binding.tvFileName.text = file.name
      binding.root.setOnClickListener {
        setSelectedItem(adapterPosition)
        onItemClickListener?.onItemSelected(file)
      }
    }

    private fun setSelectedItem(position: Int) {
      val prevPosition = lastClickedPosition
      lastClickedPosition = position
      notifyItemChanged(prevPosition)
      notifyItemChanged(lastClickedPosition)
    }

    fun setSelectedItem(active: Boolean) {
      binding.vHighlight.isActivated = active
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder =
    MViewHolder(
      ItemRvFolderBackupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

  override fun getItemCount(): Int = files.size

  override fun onBindViewHolder(holder: MViewHolder, position: Int) {
    holder.setSelectedItem(lastClickedPosition == position)
    holder.bind(files[position])
  }
}