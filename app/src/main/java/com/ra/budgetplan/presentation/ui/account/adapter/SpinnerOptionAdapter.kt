package com.ra.budgetplan.presentation.ui.account.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ra.budgetplan.R

class SpinnerOptionAdapter(
  context: Context,
  private val options: MutableList<String> = mutableListOf("Edit", "Delete") // Default Option
): ArrayAdapter<String>(context, 0, options) {

  private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    return convertView ?: layoutInflater.inflate(R.layout.item_spinner_option_adapter, parent, false)
  }

  override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
    return when(position) {
      0 -> View(context)
      else -> {
        val view = layoutInflater.inflate(R.layout.item_spinner_option_adapter, parent, false)
        getItem(position)?.let { setView(view, it) }
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

  override fun getItem(position: Int): String? {
    return options[position]
  }

  private fun setView(view: View, text: String) {
    val tvOption = view.findViewById<TextView>(R.id.tv_option)
    tvOption.text = text
  }
}