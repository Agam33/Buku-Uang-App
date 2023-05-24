package com.ra.budgetplan.presentation.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.budgetplan.databinding.FragmentAccountBinding
import com.ra.budgetplan.domain.model.AkunModel
import com.ra.budgetplan.presentation.ui.account.adapter.RvAccountAdapter
import com.ra.budgetplan.customview.spinner.SpinnerItemOptions
import com.ra.budgetplan.presentation.viewmodel.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment(), RvAccountAdapter.OnOptionAccountClickCallBack {

  private var _binding: FragmentAccountBinding? = null
  private val binding get() = _binding

  private val accountAdapter = RvAccountAdapter()

  private val viewModel: AccountViewModel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    createNewAccount()
    setupAccountList()
    observer()
  }

  private fun setupAccountList() {
    accountAdapter.onOptionAccountClickCallBack = this@AccountFragment
    viewModel.accounts.observe(viewLifecycleOwner) {
      accountAdapter.submitList(it)
    }

    binding?.run {
      rvAccount.apply {
        adapter = accountAdapter
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(requireContext())
      }
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    _binding = FragmentAccountBinding.inflate(inflater, container, false)
    return binding?.root
  }

  private fun observer() {
    binding?.lifecycleOwner = viewLifecycleOwner
    binding?.vm = viewModel

    viewModel.emptyMessageState.observe(viewLifecycleOwner) {
      binding?.emptyLayout?.state = it
    }
  }

  private fun createNewAccount() {
    binding?.run {
      fabAddAccount.setOnClickListener {
        val i = Intent(requireActivity(), CreateNewAccountActivity::class.java).apply {
          putExtra(EXTRA_TEXT, CREATE_ACCOUNT)
        }
        startActivity(i)
      }
    }
  }

  override fun onDestroy() {
    _binding = null
    super.onDestroy()
  }

  override fun option(options: SpinnerItemOptions, akun: AkunModel) {
    when(options) {
      SpinnerItemOptions.DELETE -> {
        viewModel.deleteAccount(akun)
      }
      SpinnerItemOptions.EDIT -> {
        val i = Intent(requireActivity(), CreateNewAccountActivity::class.java).apply {
          putExtra(EXTRA_TEXT, EDIT_ACCOUNT)
          putExtra(ACCOUNT_MODEL, akun)
        }
        startActivity(i)
      }
    }
  }

  companion object {
    const val EDIT_ACCOUNT = "edit-account"
    const val CREATE_ACCOUNT = "create-account"
    const val ACCOUNT_MODEL = "account-model"
    const val EXTRA_TEXT = "extra-text"
  }
}