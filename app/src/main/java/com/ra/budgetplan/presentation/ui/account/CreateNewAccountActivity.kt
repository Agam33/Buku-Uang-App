package com.ra.budgetplan.presentation.ui.account

import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ra.budgetplan.R
import com.ra.budgetplan.customview.dialog.icon.Icon
import com.ra.budgetplan.customview.dialog.icon.IconCategory
import com.ra.budgetplan.customview.dialog.icon.IconListDialog
import com.ra.budgetplan.databinding.ActivityCreateNewAccountBinding
import com.ra.budgetplan.domain.model.AkunModel
import com.ra.budgetplan.presentation.ui.account.AccountFragment.Companion.ACCOUNT_MODEL
import com.ra.budgetplan.presentation.ui.account.AccountFragment.Companion.CREATE_ACCOUNT
import com.ra.budgetplan.presentation.ui.account.AccountFragment.Companion.EDIT_ACCOUNT
import com.ra.budgetplan.presentation.ui.account.AccountFragment.Companion.EXTRA_TEXT
import com.ra.budgetplan.presentation.viewmodel.AccountViewModel
import com.ra.budgetplan.util.parcelable
import com.ra.budgetplan.util.setupNoActionbar
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.util.UUID

@AndroidEntryPoint
class CreateNewAccountActivity : AppCompatActivity(), IconListDialog.OnClickItemListener {

  private val binding: ActivityCreateNewAccountBinding by lazy {
    ActivityCreateNewAccountBinding.inflate(layoutInflater)
  }

  private val viewModel: AccountViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    if(isAccountCreated()) {
      setupDetailAccount()
    }

    setupNoActionbar(binding.toolbar)
    chooseIcon()
  }

  private fun isAccountCreated(): Boolean = intent?.getStringExtra(EXTRA_TEXT) == EDIT_ACCOUNT

  private fun setupDetailAccount() {
    val account = intent?.parcelable<AkunModel>(ACCOUNT_MODEL)
    account?.let {
      binding.edtInputLayoutName.editText?.setText(account.nama)
      binding.edtInputInitialAmount.text = Editable.Factory.getInstance().newEditable(account.total.toString())
      binding.ivIcon.setImageResource(account.icon)
    }
  }

  private fun chooseIcon() {
    binding.btnChooseIcon.setOnClickListener {
      val dialogIcon = IconListDialog()
      dialogIcon.category = IconCategory.ACCOUNT
      dialogIcon.onClickItemListener = this@CreateNewAccountActivity
      dialogIcon.show(supportFragmentManager, ICON_LIST_IN_CREATE_ACCOUNT)
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

    if(binding.ivIcon.tag == null) {
      Toast.makeText(this@CreateNewAccountActivity, "Pilih Icon!!", Toast.LENGTH_SHORT).show()
      return
    }

    when(intent?.getStringExtra(EXTRA_TEXT)) {
      EDIT_ACCOUNT -> {
        val account = intent?.parcelable<AkunModel>(ACCOUNT_MODEL)
        account?.let {
          it.icon = binding.ivIcon.tag as Int
          it.nama = accountName
          it.total = amount.toInt()
          it.updatedAt = LocalDateTime.now()
          viewModel.updateAccount(it)
        }
      }
      CREATE_ACCOUNT -> {
        val akun = AkunModel(
          uuid = UUID.randomUUID(),
          icUrl = "",
          icon = binding.ivIcon.tag as Int,
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

  override fun getIcon(icon: Icon) {
    binding.ivIcon.setImageResource(icon.icon)
    binding.ivIcon.tag = icon.icon
  }

  companion object {
    private const val ICON_LIST_IN_CREATE_ACCOUNT = "ICON_LIST_IN_CREATE_ACCOUNT"
  }
}