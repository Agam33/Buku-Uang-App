package com.ra.budgetplan.presentation.ui.analytics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ra.budgetplan.R
import com.ra.budgetplan.databinding.FragmentAnalyticBinding

class AnalyticFragment : Fragment() {

  private var _binding: FragmentAnalyticBinding? = null
  private val binding get() = _binding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    _binding = FragmentAnalyticBinding.inflate(inflater, container, false)
    return binding?.root
  }

  override fun onDestroy() {
    _binding = null
    super.onDestroy()
  }
}