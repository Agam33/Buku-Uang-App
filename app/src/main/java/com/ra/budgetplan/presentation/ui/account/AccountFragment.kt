package com.ra.budgetplan.presentation.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ra.budgetplan.R
import com.ra.budgetplan.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {

  private var _binding: FragmentAccountBinding? = null
  private val binding get() = _binding

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
 }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    _binding = FragmentAccountBinding.inflate(inflater, container, false)
    return binding?.root
  }

  override fun onDestroy() {
    _binding = null
    super.onDestroy()
  }
}