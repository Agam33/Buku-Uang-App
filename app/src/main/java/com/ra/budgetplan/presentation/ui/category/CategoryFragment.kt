package com.ra.budgetplan.presentation.ui.category

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.budgetplan.customview.spinner.SpinnerItemOptions
import com.ra.budgetplan.databinding.FragmentCategoryBinding
import com.ra.budgetplan.domain.entity.TipeKategori
import com.ra.budgetplan.domain.model.KategoriModel
import com.ra.budgetplan.presentation.ui.category.adapter.RvGroupCategoryAdapter
import com.ra.budgetplan.presentation.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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
    createCategory()
  }

  private fun createCategory() {
    binding?.fabAddCategory?.setOnClickListener {
      startActivity(Intent(requireContext(), CreateCategoryActivity::class.java))
    }
  }

  private fun observer() {
    binding?.lifecycleOwner = viewLifecycleOwner
    binding?.vm = viewModel
    viewModel.setCategories()
    viewModel.mapCategory.observe(viewLifecycleOwner) {
      setupListCategory(it)
    }
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
    Timber.e(data?.keys.toString())
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
          // TODO: EDIT
        }
        SpinnerItemOptions.DELETE -> {
          viewModel.deleteCategory(kategoriModel)
        }
      }
  }
}