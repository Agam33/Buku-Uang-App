package com.ra.bkuang.presentation.ui.transaction.adapter

import androidx.recyclerview.widget.RecyclerView
import com.ra.bkuang.databinding.ItemRvExpenseDaysBinding
import com.ra.bkuang.databinding.ItemRvIncomeDaysBinding
import com.ra.bkuang.databinding.ItemRvTransferDaysBinding
import com.ra.bkuang.data.entity.DetailPendapatan
import com.ra.bkuang.data.entity.DetailPengeluaran
import com.ra.bkuang.data.entity.DetailTransfer
import com.ra.bkuang.util.Extension.toFormatRupiah

/*
    Expense DayViewHolder
 */
class ExpenseDayViewHolder(
  val binding: ItemRvExpenseDaysBinding
): RecyclerView.ViewHolder(binding.root) {

  fun bind(model: DetailPengeluaran) {
    binding.tvAccount.text = model.akun.nama
    binding.titleItem.text = model.kategori.nama
    binding.tvMoney.text = model.pengeluaran.jumlah.toFormatRupiah()

  }
}

/*
    Income DayViewHolder
 */
class IncomeDayViewHolder(
  val binding: ItemRvIncomeDaysBinding
): RecyclerView.ViewHolder(binding.root) {

  fun bind(model: DetailPendapatan) {
    binding.tvAccount.text = model.akun.nama
    binding.titleItem.text = model.kategori.nama
    binding.tvMoney.text = model.pendapatan.jumlah.toFormatRupiah()
  }
}

/*
    Transfer DayViewHolder
 */
class TransferDayViewHolder(
  val binding: ItemRvTransferDaysBinding
): RecyclerView.ViewHolder(binding.root) {
  fun bind(model: DetailTransfer) {
    binding.tvToAccount.text = model.toAkun.nama
    binding.tvFromAccount.text = model.fromAkun.nama
    binding.tvMoney.text = model.transfer.jumlah.toFormatRupiah()
  }
}