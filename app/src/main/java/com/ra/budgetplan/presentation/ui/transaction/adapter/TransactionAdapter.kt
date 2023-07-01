package com.ra.budgetplan.presentation.ui.transaction.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ra.budgetplan.databinding.ItemRvExpenseMonthBinding
import com.ra.budgetplan.databinding.ItemRvIncomeMonthBinding
import com.ra.budgetplan.databinding.ItemRvTransferMonthBinding
import com.ra.budgetplan.domain.entity.DetailPendapatan
import com.ra.budgetplan.domain.entity.DetailPengeluaran
import com.ra.budgetplan.domain.entity.DetailTransfer
import com.ra.budgetplan.util.RvGroup


/*
    Expense Adapter
 */
class ExpenseRvAdapter(
  private val group:  RvGroup<String, ArrayList<DetailPengeluaran>>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  var onDayItemClickListener: OnDayItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return ExpenseMonthViewHolder(
          ItemRvExpenseMonthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
  }

  override fun getItemCount(): Int = group.size

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val monthHolder = holder as ExpenseMonthViewHolder
    monthHolder.onDayItemClickListener = onDayItemClickListener
    monthHolder.bind(group.keyList[position], group.getValueByIndex(position) ?: arrayListOf())
  }
}


/*
    Income Adapter
 */
class IncomeRvAdapter(
  private val group: RvGroup<String, ArrayList<DetailPendapatan>>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  var onDayItemClickListener: OnDayItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return IncomeMonthViewHolder(
      ItemRvIncomeMonthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
  }

  override fun getItemCount(): Int = group.size

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val monthHolder = holder as IncomeMonthViewHolder
    monthHolder.onDayItemClickListener = onDayItemClickListener
    monthHolder.bind(group.keyList[position], group.getValueByIndex(position) ?: arrayListOf())
  }
}


/*
    Transfer Adapter
 */
class TransferRvAdapter(
  private val group: RvGroup<String, ArrayList<DetailTransfer>>
):  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  var onDayItemClickListener: OnDayItemClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return TransferMonthViewHolder(
      ItemRvTransferMonthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
  }

  override fun getItemCount(): Int = group.size

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val monthHolder = holder as TransferMonthViewHolder
    monthHolder.onDayItemClickListener = onDayItemClickListener
    monthHolder.bind(group.keyList[position], group.getValueByIndex(position) ?: arrayListOf())
  }
}