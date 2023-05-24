package com.ra.budgetplan.presentation.ui.category

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.budgetplan.R
import com.ra.budgetplan.databinding.ActivityCreateCategoryBinding
import com.ra.budgetplan.domain.entity.TipeKategori
import com.ra.budgetplan.domain.model.IconModel
import com.ra.budgetplan.domain.model.KategoriModel
import com.ra.budgetplan.presentation.ui.category.adapter.RvIconCategoryAdapter
import com.ra.budgetplan.presentation.viewmodel.CategoryViewModel
import com.ra.budgetplan.util.setupNoActionbar
import com.ra.budgetplan.util.shortToast
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.util.UUID

@AndroidEntryPoint
class CreateCategoryActivity : AppCompatActivity(), RvIconCategoryAdapter.OnItemSelectedListener {

  private val binding: ActivityCreateCategoryBinding by lazy {
    ActivityCreateCategoryBinding.inflate(layoutInflater)
  }

  private val viewModel: CategoryViewModel by viewModels()

  private var iconId: Int = -1
  private var currentCategory = TipeKategori.PENDAPATAN

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    observer()
    setupCategory()
    setupNoActionbar(binding.toolbar)
  }

  private fun observer() {
    viewModel.setCurrentCategory(currentCategory)
    viewModel.listIcon.observe(this@CreateCategoryActivity) {
      setupListIcon(it)
    }
    viewModel.currCategory.observe(this@CreateCategoryActivity) {
      currentCategory = it
    }
  }

  private fun setupListIcon(list: List<IconModel>) {
    val rvIconCategoryAdapter = RvIconCategoryAdapter()
    rvIconCategoryAdapter.submitList(list)
    rvIconCategoryAdapter.onItemSelectedListener = this@CreateCategoryActivity
    binding.rvIcons.apply {
      adapter = rvIconCategoryAdapter
      layoutManager = GridLayoutManager(this@CreateCategoryActivity, 2, LinearLayoutManager.HORIZONTAL, false)
      setHasFixedSize(true)
    }
  }

  private fun setupCategory() {
    binding.toggleBtn.addOnButtonCheckedListener { group, checkedId, isChecked ->
      if(isChecked) {
        when(checkedId) {
          binding.btnExpense.id -> {
            viewModel.setCurrentCategory(TipeKategori.PENGELUARAN)
          }
          binding.btnIncome.id -> {
            viewModel.setCurrentCategory(TipeKategori.PENDAPATAN)
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

    val category = KategoriModel(
      uuid = UUID.randomUUID(),
      icUrl = "",
      icon = iconId,
      nama = name,
      tipeKategori = currentCategory,
      updatedAt = LocalDateTime.now(),
      createdAt = LocalDateTime.now()
    )

    shortToast(getString(R.string.msg_success))

    viewModel.saveKategori(category)
    onBackPressedDispatcher.onBackPressed()
  }

  override fun onItemSelected(position: Int, iconModel: IconModel) {
    iconId = iconModel.icon
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
}