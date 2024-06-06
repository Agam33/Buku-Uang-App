package com.ra.bkuang.features.category.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseFragment
import com.ra.bkuang.common.util.ActionType
import com.ra.bkuang.common.util.Extension.showShortToast
import com.ra.bkuang.common.view.dialog.CautionDeleteDialog
import com.ra.bkuang.common.view.spinner.SpinnerItemOptions
import com.ra.bkuang.databinding.FragmentCategoryBinding
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.category.presentation.CreateCategoryActivity.Companion.CATEGORY_TYPE
import com.ra.bkuang.features.category.presentation.CreateCategoryActivity.Companion.CREATE_OR_EDIT_CATEGORY
import com.ra.bkuang.features.category.presentation.CreateCategoryActivity.Companion.EXTRA_BUNDLE_CLAZZ
import com.ra.bkuang.features.category.presentation.adapter.RvGroupCategoryAdapter
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(R.layout.fragment_category), RvGroupCategoryAdapter.OnOptionCategoryClickCallBack {

  @Inject lateinit var groupCategoryAdapter: RvGroupCategoryAdapter

  private val viewModel: CategoryViewModel by viewModels()

  private val deleteDialog = CautionDeleteDialog()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    observer()
    refresh()
    createCategory()
  }

  private fun createCategory() {
    binding?.fabAddCategory?.setOnClickListener {
      val i = Intent(requireContext(), CreateCategoryActivity::class.java).apply {
        putExtra(CREATE_OR_EDIT_CATEGORY, ActionType.CREATE.name)
      }
      startActivity(i)
    }
  }

  private fun observer() {
    binding?.lifecycleOwner = viewLifecycleOwner
    binding?.vm = viewModel

    lifecycleScope.launch {
      viewModel.categoryUiState.collect { uiState ->
        setupListCategory(uiState.mapCategory)

        uiState.isSuccessfulDelete?.let {
          if(it) {
            refresh()
            showShortToast(requireContext().resources.getString(R.string.msg_success))
            deleteDialog.dismiss()
          } else {
            showShortToast(getString(R.string.msg_failed_delete))
          }
        }
      }
    }
  }

  private fun refresh() {
    viewModel.setCategories()
  }

  private fun setupListCategory(data :Map<TransactionType, List<KategoriModel>>) {
    groupCategoryAdapter.mapCategory = data
    groupCategoryAdapter.onOptionCategoryClickCallBack = this@CategoryFragment
    binding?.run {
      rvCategories.apply {
        adapter = groupCategoryAdapter
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(requireContext())
      }
    }
  }

  override fun option(options: SpinnerItemOptions, kategoriModel: KategoriModel) {
      when(options) {
        SpinnerItemOptions.EDIT -> {
          val i = Intent(requireContext(), CreateCategoryActivity::class.java).apply {
            putExtra(CREATE_OR_EDIT_CATEGORY, ActionType.EDIT.name)
            putExtra(EXTRA_BUNDLE_CLAZZ, kategoriModel)
            putExtra(CATEGORY_TYPE, kategoriModel.tipeKategori.name)
          }
          startActivity(i)
        }
        SpinnerItemOptions.DELETE -> {

          deleteDialog.onOptionItemClick = object : CautionDeleteDialog.OnOptionItemClick {
            override fun onDelete() { viewModel.deleteCategory(kategoriModel) }
            override fun onCancel() { deleteDialog.dismiss() }
          }

          deleteDialog.show(parentFragmentManager, "Delete Dialog")
        }
      }
  }
}