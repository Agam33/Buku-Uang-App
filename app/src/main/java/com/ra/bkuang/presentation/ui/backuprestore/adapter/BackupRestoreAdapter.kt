package com.ra.bkuang.presentation.ui.backuprestore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ra.bkuang.databinding.ItemRvBackupRestoreTipsBinding

class BackupRestoreAdapter(
  private val tips: List<String>
): RecyclerView.Adapter<BackupRestoreAdapter.MViewHolder>() {

  inner class MViewHolder(
    private val binding: ItemRvBackupRestoreTipsBinding
  ):  RecyclerView.ViewHolder(binding.root) {

    fun bind(txt: String) {
      binding.tvDesc.text = txt
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder =
    MViewHolder(
      ItemRvBackupRestoreTipsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

  override fun getItemCount(): Int = tips.size

  override fun onBindViewHolder(holder: MViewHolder, position: Int) {
    holder.bind(tips[position])
  }
}