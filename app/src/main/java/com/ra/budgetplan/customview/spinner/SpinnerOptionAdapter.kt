package com.ra.budgetplan.customview.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ra.budgetplan.R

class SpinnerOptionAdapter(
  context: Context,
): ArrayAdapter<SpinnerItemOptions>(context, 0, SpinnerItemOptions.values()) {

  private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    return convertView ?: layoutInflater.inflate(R.layout.item_spinner_option_adapter, parent, false)
  }

  override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
    return when(position) {
      0 -> View(context)
      else -> {
        val view = layoutInflater.inflate(R.layout.item_spinner_option_adapter, parent, false)
        setView(view, getItem(position))
        view
      }
    }
  }

  override fun getCount(): Int {
    return super.getCount() + 1
  }

  override fun isEnabled(position: Int): Boolean {
    return position != 0
  }

  override fun getItem(position: Int): SpinnerItemOptions =
    SpinnerItemOptions.values()[position - 1]

  private fun setView(view: View, text: SpinnerItemOptions) {
    val tvOption = view.findViewById<TextView>(R.id.tv_option)
    tvOption.text = text.toString()
  }
}