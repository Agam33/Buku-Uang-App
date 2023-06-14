package com.ra.budgetplan.presentation.ui.transaction.adapter

import androidx.recyclerview.widget.RecyclerView
import com.ra.budgetplan.databinding.ItemRvExpenseDaysBinding
import com.ra.budgetplan.databinding.ItemRvIncomeDaysBinding
import com.ra.budgetplan.databinding.ItemRvTransferDaysBinding
import com.ra.budgetplan.domain.entity.DetailPendapatan
import com.ra.budgetplan.domain.entity.DetailPengeluaran
import com.ra.budgetplan.domain.entity.DetailTransfer
import com.ra.budgetplan.util.toFormatRupiah

/*
    Expense DayViewHolder
 */
class ExpenseDayViewHolder(
  private val binding: ItemRvExpenseDaysBinding
): RecyclerView.ViewHolder(binding.root) {

  fun bind(model: DetailPengeluaran) {
    binding.ivCategory.setImageResource(model.kategori.icon)
    binding.ivAccount.setImageResource(model.akun.icon)
    binding.tvAccount.text = model.akun.nama
    binding.titleItem.text = model.kategori.nama
    binding.tvMoney.text = model.pengeluaran.jumlah.toFormatRupiah()
  }
}

/*
    Income DayViewHolder
 */
class IncomeDayViewHolder(
  private val binding: ItemRvIncomeDaysBinding
): RecyclerView.ViewHolder(binding.root) {

  fun bind(model: DetailPendapatan) {
    binding.ivCategory.setImageResource(model.kategori.icon)
    binding.ivAccount.setImageResource(model.akun.icon)
    binding.tvAccount.text = model.akun.nama
    binding.titleItem.text = model.kategori.nama
    binding.tvMoney.text = model.pendapatan.jumlah.toFormatRupiah()
  }
}

/*
    Transfer DayViewHolder
 */
class TransferDayViewHolder(
  private val binding: ItemRvTransferDaysBinding
): RecyclerView.ViewHolder(binding.root) {

  fun bind(model: DetailTransfer) {
    binding.ivFromAccount.setImageResource(model.fromAkun.icon)
    binding.ivToAccount.setImageResource(model.toAkun.icon)
    binding.tvToAccount.text = model.toAkun.nama
    binding.tvFromAccount.text = model.fromAkun.nama
    binding.tvMoney.text = model.transfer.jumlah.toFormatRupiah()
  }
}


