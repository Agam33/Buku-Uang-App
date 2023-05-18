package com.ra.budgetplan.presentation.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ra.budgetplan.R
import com.ra.budgetplan.databinding.FragmentCategoryBinding


class CategoryFragment : Fragment() {

  private var _binding: FragmentCategoryBinding? = null
  private val binding get() = _binding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    _binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
    return binding?.root
  }

  override fun onDestroy() {
    _binding = null
    super.onDestroy()
  }
}