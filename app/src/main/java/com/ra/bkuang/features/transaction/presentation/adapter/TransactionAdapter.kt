package com.ra.bkuang.features.transaction.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ra.bkuang.databinding.ItemRvExpenseMonthBinding
import com.ra.bkuang.databinding.ItemRvIncomeMonthBinding
import com.ra.bkuang.databinding.ItemRvTransferMonthBinding
import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import com.ra.bkuang.features.transaction.data.entity.DetailPengeluaran
import com.ra.bkuang.features.transaction.data.entity.DetailTransfer
import com.ra.bkuang.features.transaction.domain.model.TransactionGroup


/*
    Expense Adapter
 */
class ExpenseRvAdapter(
  private val group: TransactionGroup<String, ArrayList<DetailPengeluaran>>
): RecyclerView.Adapter<ExpenseMonthViewHolder>() {

  var onDayItemClickListener: OnDayItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseMonthViewHolder {
    return ExpenseMonthViewHolder(
          ItemRvExpenseMonthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
  }

  override fun getItemCount(): Int = group.size

  override fun onBindViewHolder(holder: ExpenseMonthViewHolder, position: Int) {
    holder.onDayItemClickListener = onDayItemClickListener
    holder.bind(group.keyList[position], group.getValueByIndex(position) ?: arrayListOf())
  }
}


/*
    Income Adapter
 */
class IncomeRvAdapter(
  private val group: TransactionGroup<String, ArrayList<DetailPendapatan>>
): RecyclerView.Adapter<IncomeMonthViewHolder>() {

  var onDayItemClickListener: OnDayItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeMonthViewHolder {
    return IncomeMonthViewHolder(
      ItemRvIncomeMonthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
  }

  override fun getItemCount(): Int = group.size

  override fun onBindViewHolder(holder: IncomeMonthViewHolder, position: Int) {
    holder.onDayItemClickListener = onDayItemClickListener
    holder.bind(group.keyList[position], group.getValueByIndex(position) ?: arrayListOf())
  }
}


/*
    Transfer Adapter
 */
class TransferRvAdapter(
  private val group: TransactionGroup<String, ArrayList<DetailTransfer>>
):  RecyclerView.Adapter<TransferMonthViewHolder> () {

  var onDayItemClickListener: OnDayItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransferMonthViewHolder {
    return TransferMonthViewHolder(
      ItemRvTransferMonthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
  }

  override fun getItemCount(): Int = group.size

  override fun onBindViewHolder(holder: TransferMonthViewHolder, position: Int) {
    holder.onDayItemClickListener = onDayItemClickListener
    holder.bind(group.keyList[position], group.getValueByIndex(position) ?: arrayListOf())
  }
}