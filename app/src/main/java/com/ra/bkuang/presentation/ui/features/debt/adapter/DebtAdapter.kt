package com.ra.bkuang.presentation.ui.features.debt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ra.bkuang.R
import com.ra.bkuang.databinding.ItemRvDebtBinding
import com.ra.bkuang.domain.model.HutangModel
import com.ra.bkuang.presentation.util.Constants.DATE_TIME_FORMATTER
import com.ra.bkuang.presentation.util.Extension.collapsedHeight
import com.ra.bkuang.presentation.util.Extension.expandedHeight
import com.ra.bkuang.presentation.util.Extension.isOverBudget
import com.ra.bkuang.presentation.util.Extension.toFormatRupiah
import com.ra.bkuang.presentation.util.Extension.toPercent
import com.ra.bkuang.presentation.util.Extension.toPercentText
import com.ra.bkuang.presentation.util.Extension.toStringFormat

class DebtAdapter: ListAdapter<HutangModel, DebtAdapter.MViewHolder>(DIFF) {

  interface OnIconDeleteListener {
    fun setOnItemDelete(model: HutangModel, adapterPosition: Int)
  }

  interface OnIconAlarmListener {
    fun setOnItemAlarm(model: HutangModel, adapterPosition: Int)
  }

  interface OnIconEditListener {
    fun setOnItemEdit(model: HutangModel)
  }

  interface OnItemClickListener {
    fun setOnItemClickCallback(model: HutangModel)
  }

  var setIconDeleteListener: OnIconDeleteListener? = null
  var setIconAlarmListener: OnIconAlarmListener? = null
  var setIconEditListener: OnIconEditListener? = null
  var setItemClickListener: OnItemClickListener? = null

  inner class MViewHolder(
    private val binding: ItemRvDebtBinding
  ): RecyclerView.ViewHolder(binding.root) {

    private var isExpanded = false

    fun bind(model: HutangModel) {
      binding.run {
        tvTitleDebt.text = model.nama
        tvCurrentMoney.text = model.totalPengeluaran.toFormatRupiah()
        tvGoalMoney.text = model.maxCicilan.toFormatRupiah()
        tvDesc.text = model.deskripsi
        tvDueDate.text = String.format(binding.root.context.getString(R.string.txt_due_date_format), model.jatuhTempo.toStringFormat(DATE_TIME_FORMATTER))

        val percent = model.totalPengeluaran.toPercent(model.maxCicilan)

        tvPercent.text = percent.toPercentText()
        goalProgress.progress = percent.toInt()

        goalProgress.setIndicatorColor(
          if (model.totalPengeluaran.isOverBudget(model.maxCicilan)) binding.root.context.getColor(R.color.red_400)
          else binding.root.context.getColor(R.color.indigo_50)
        )

        imgBtnAlarm.setImageResource(
          if(model.pengingatAktif) {
            R.drawable.active_alarm_on_24
          } else {
           R.drawable.inactive_round_alarm_24
          }
        )

        binding.root.setOnClickListener {
          setItemClickListener?.setOnItemClickCallback(model)
        }

        imgBtnAlarm.setOnClickListener {
          setIconAlarmListener?.setOnItemAlarm(model, adapterPosition)
        }

        imgBtnDelete.setOnClickListener {
          setIconDeleteListener?.setOnItemDelete(model, adapterPosition)
        }

        imgBtnEdit.setOnClickListener {
          setIconEditListener?.setOnItemEdit(model)
        }

        imgCloseOpenLayout.setOnClickListener {
          if (isExpanded) {
            imgCloseOpenLayout.setImageResource(R.drawable.baseline_keyboard_double_arrow_down_24)
            layoutDetail.collapsedHeight(600)
          } else {
            imgCloseOpenLayout.setImageResource(R.drawable.baseline_keyboard_double_arrow_up_24)
            layoutDetail.expandedHeight(600)
          }

          isExpanded = !isExpanded
        }
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
    return MViewHolder(
      ItemRvDebtBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
  }

  override fun onBindViewHolder(holder: MViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  companion object {
    private val DIFF = object : DiffUtil.ItemCallback<HutangModel>() {
      override fun areItemsTheSame(oldItem: HutangModel, newItem: HutangModel): Boolean {
        return oldItem == newItem
      }

      override fun areContentsTheSame(oldItem: HutangModel, newItem: HutangModel): Boolean {
        return oldItem.uuid == newItem.uuid
      }
    }
  }
}