package com.ra.budgetplan.util

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:hide")
fun hideView(view: View, state: Boolean) {
  view.visibility = if (state) View.GONE else View.VISIBLE
}