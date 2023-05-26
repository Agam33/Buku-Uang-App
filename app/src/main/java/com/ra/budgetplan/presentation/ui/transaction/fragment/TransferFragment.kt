package com.ra.budgetplan.presentation.ui.transaction.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ra.budgetplan.databinding.FragmentTransferBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferFragment : Fragment() {

  private var _binding: FragmentTransferBinding? = null
  val binding = _binding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    _binding = FragmentTransferBinding.inflate(inflater, container, false)
    return binding?.root
  }


  override fun onDestroy() {
    _binding = null
    super.onDestroy()
  }
}