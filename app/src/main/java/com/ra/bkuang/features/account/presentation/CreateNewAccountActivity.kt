package com.ra.bkuang.features.account.presentation

import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.ra.bkuang.R
import com.ra.bkuang.databinding.ActivityCreateNewAccountBinding
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.common.base.BaseActivity
import com.ra.bkuang.features.account.presentation.AccountFragment.Companion.ACCOUNT_MODEL
import com.ra.bkuang.features.account.presentation.AccountFragment.Companion.CREATE_ACCOUNT
import com.ra.bkuang.features.account.presentation.AccountFragment.Companion.EDIT_ACCOUNT
import com.ra.bkuang.features.account.presentation.AccountFragment.Companion.EXTRA_TEXT
import com.ra.bkuang.common.util.Extension.parcelable
import com.ra.bkuang.common.util.Extension.setupNoActionbar
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.util.UUID

@AndroidEntryPoint
class CreateNewAccountActivity : BaseActivity<ActivityCreateNewAccountBinding>(R.layout.activity_create_new_account) {

  private val viewModel: AccountViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if(isAccountCreated()) {
      setupDetailAccount()
    }

    setupNoActionbar(binding.toolbar)
  }

  private fun isAccountCreated(): Boolean = intent?.getStringExtra(EXTRA_TEXT) == EDIT_ACCOUNT

  private fun setupDetailAccount() {
    val account = intent?.parcelable<AkunModel>(ACCOUNT_MODEL)
    account?.let {
      binding.edtInputLayoutName.editText?.setText(account.nama)
      binding.edtInputInitialAmount.text = Editable.Factory.getInstance().newEditable(account.total.toString())
    }
  }

  private fun saveAccount() {
    val accountName = binding.edtInputName.text.toString()
    val amount = binding.edtInputInitialAmount.text.toString()

    if(accountName.isBlank()) {
      binding.edtInputLayoutName.error = getString(R.string.msg_empty)
      return
    }

    if(amount.isBlank()) {
      binding.edtInputInitialAmount.error = getString(R.string.msg_empty)
      return
    }

    when(intent?.getStringExtra(EXTRA_TEXT)) {
      EDIT_ACCOUNT -> {
        val account = intent?.parcelable<AkunModel>(ACCOUNT_MODEL)
        account?.let {
          it.nama = accountName
          it.total = amount.toInt()
          it.updatedAt = LocalDateTime.now()
          viewModel.updateAccount(it)
        }
      }

      CREATE_ACCOUNT -> {
        val akun = AkunModel(
          uuid = UUID.randomUUID(),
          nama = accountName,
          total = amount.toInt(),
          createdAt = LocalDateTime.now(),
          updatedAt = LocalDateTime.now(),
        )
        viewModel.createAccount(akun)
      }
    }
    onBackPressedDispatcher.onBackPressed()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.create_account_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when(item.itemId) {
      android.R.id.home -> {
        onBackPressedDispatcher.onBackPressed()
        true
      }
      R.id.menu_done -> {
        saveAccount()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}