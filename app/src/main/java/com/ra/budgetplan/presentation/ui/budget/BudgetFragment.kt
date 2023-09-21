package com.ra.budgetplan.presentation.ui.budget

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ra.budgetplan.R
import com.ra.budgetplan.databinding.FragmentBudgetBinding
import com.ra.budgetplan.presentation.ui.budget.fragment.MonthBudgetFragment


class BudgetFragment : Fragment() {

  private var _binding: FragmentBudgetBinding? = null
  private val binding get() = _binding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    childFragmentManager.beginTransaction()
      .replace(R.id.fragment_container, MonthBudgetFragment())
      .commit()
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    _binding = FragmentBudgetBinding.inflate(inflater, container, false)
    return binding?.root
  }

  override fun onDestroy() {
    _binding = null
    super.onDestroy()
  }
}