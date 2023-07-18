package com.ra.budgetplan.presentation.ui.category

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.budgetplan.R
import com.ra.budgetplan.customview.dialog.CautionDeleteDialog
import com.ra.budgetplan.customview.spinner.SpinnerItemOptions
import com.ra.budgetplan.databinding.FragmentCategoryBinding
import com.ra.budgetplan.domain.entity.TipeKategori
import com.ra.budgetplan.domain.model.KategoriModel
import com.ra.budgetplan.presentation.ui.category.CreateCategoryActivity.Companion.CATEGORY_TYPE
import com.ra.budgetplan.presentation.ui.category.CreateCategoryActivity.Companion.CREATE_OR_EDIT_CATEGORY
import com.ra.budgetplan.presentation.ui.category.CreateCategoryActivity.Companion.EXTRA_BUNDLE_CLAZZ
import com.ra.budgetplan.presentation.ui.category.adapter.RvGroupCategoryAdapter
import com.ra.budgetplan.presentation.viewmodel.CategoryViewModel
import com.ra.budgetplan.util.ActionType
import com.ra.budgetplan.util.StatusItem
import com.ra.budgetplan.util.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment(),
  RvGroupCategoryAdapter.OnOptionCategoryClickCallBack {

  private var _binding: FragmentCategoryBinding? = null

  private val binding get() = _binding

  private lateinit var groupCategoryAdapter: RvGroupCategoryAdapter

  private val viewModel: CategoryViewModel by viewModels()

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
    viewModel.mapCategory.observe(viewLifecycleOwner) {
      setupListCategory(it)
    }
  }

  private fun refresh() {
    viewModel.setCategories()
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

  private fun setupListCategory(data :HashMap<TipeKategori, List<KategoriModel>>?) {
    groupCategoryAdapter = RvGroupCategoryAdapter()
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
          val deleteDialog = CautionDeleteDialog()

          deleteDialog.onOptionItemClick = object : CautionDeleteDialog.OnOptionItemClick {
            override fun onDelete() {
              viewLifecycleOwner.lifecycleScope.launch {
                viewModel.deleteCategory(kategoriModel).collect { status ->
                  when(status) {
                    StatusItem.SUCCESS -> {
                      refresh()
                      showShortToast(requireContext().resources.getString(R.string.msg_success))
                      deleteDialog.dismiss()
                    }
                    StatusItem.LOADING -> {}
                    StatusItem.FAILED -> {}
                  }
                }
              }
            }
            override fun onCancel() { deleteDialog.dismiss() }
          }

          deleteDialog.show(parentFragmentManager, "Delete Dialog")
        }
      }
  }
}