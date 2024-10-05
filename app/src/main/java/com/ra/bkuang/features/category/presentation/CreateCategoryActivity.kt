package com.ra.bkuang.features.category.presentation

import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.ra.bkuang.R
import com.ra.bkuang.databinding.ActivityCreateCategoryBinding
import com.ra.bkuang.features.category.data.model.KategoriModel
import com.ra.bkuang.common.base.BaseActivity
import com.ra.bkuang.common.util.ActionType
import com.ra.bkuang.common.util.Extension.parcelable
import com.ra.bkuang.common.util.Extension.setupActionBar
import com.ra.bkuang.common.util.Extension.showShortToast
import com.ra.bkuang.common.util.getActionType
import com.ra.bkuang.features.category.presentation.viewmodel.CategoryViewModel
import com.ra.bkuang.core.data.source.local.database.entity.TransactionType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID

@AndroidEntryPoint
class CreateCategoryActivity : BaseActivity<ActivityCreateCategoryBinding>(R.layout.activity_create_category) {

  private val viewModel: CategoryViewModel by viewModels()

  private var currentCategory = TransactionType.PENDAPATAN

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    observer()
    setupCategory()

    if(isEditCategory()) {
      setupUpdate()
    } else {
      viewModel.setCurrentCategory(TransactionType.PENDAPATAN)
    }

    setupActionBar(binding.toolbar)
  }

  private fun setupUpdate() {
    binding.toggleBtn.visibility = View.GONE
    val category = intent?.parcelable<KategoriModel>(EXTRA_BUNDLE_CLAZZ)
    category?.let {
      viewModel.setCurrentCategory(it.tipeKategori)
      binding.edtInputName.text = Editable.Factory.getInstance().newEditable(it.nama)
    }
  }

  private fun isEditCategory(): Boolean =
    getActionType(intent?.getStringExtra(CREATE_OR_EDIT_CATEGORY) as String) == ActionType.EDIT

  private fun observer() {
    lifecycleScope.launch {
      viewModel.uiState.collect { uiState ->
        currentCategory = uiState.currCategory

        uiState.isSuccessfulSave?.let {
          if(it) {
            showShortToast(getString(R.string.txt_successful_save))
          }
        }

        uiState.isSuccessfulUpdate?.let {
          if(it) {
            showShortToast(getString(R.string.txt_successful_update))
          }
        }
      }
    }
  }

  private fun setupCategory() {
    binding.toggleBtn.addOnButtonCheckedListener { group, checkedId, isChecked ->
      if(isChecked) {
        when(checkedId) {
          binding.btnExpense.id -> {
            viewModel.setCurrentCategory(TransactionType.PENGELUARAN)
          }
          binding.btnIncome.id -> {
            viewModel.setCurrentCategory(TransactionType.PENDAPATAN)
          }
        }
      }
    }
  }

  private fun saveCategory() {
    val name = binding.edtInputName.text.toString()

    if(name.isBlank()) {
      binding.edtInputLayoutName.error = resources.getString(R.string.msg_empty)
      return
    }

    val actionType = intent?.getStringExtra(CREATE_OR_EDIT_CATEGORY) as String
    when(getActionType(actionType)) {
      ActionType.EDIT -> {
        val category = intent?.parcelable<KategoriModel>(EXTRA_BUNDLE_CLAZZ)
        category?.let {
          it.nama = name
          it.updatedAt = LocalDateTime.now()
          viewModel.updateCategory(it)
        }
      }

      ActionType.CREATE -> {
        val category = KategoriModel(
          uuid = UUID.randomUUID(),
          nama = name,
          tipeKategori = currentCategory,
          updatedAt = LocalDateTime.now(),
          createdAt = LocalDateTime.now()
        )

        showShortToast(getString(R.string.msg_success))

        viewModel.saveCategory(category)
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
        saveCategory()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  companion object {
    const val CREATE_OR_EDIT_CATEGORY = "create-or-edit-category"
    const val CATEGORY_TYPE = "category-type"
    const val EXTRA_BUNDLE_CLAZZ = "extra-bundle-clazz"
  }
}