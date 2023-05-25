package com.ra.budgetplan.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ra.budgetplan.customview.dialog.icon.IconCategory
import com.ra.budgetplan.domain.entity.TipeKategori
import com.ra.budgetplan.domain.model.IconModel
import com.ra.budgetplan.domain.model.KategoriModel
import com.ra.budgetplan.domain.usecase.akun.FindCategoryByType
import com.ra.budgetplan.domain.usecase.icon.FindIconByCategory
import com.ra.budgetplan.domain.usecase.kategori.DeleteKategori
import com.ra.budgetplan.domain.usecase.kategori.SaveKategori
import com.ra.budgetplan.domain.usecase.kategori.UpdateKategori
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
  private val deleteKategori: DeleteKategori,
  private val updateKategori: UpdateKategori,
  private val findCategoryByType: FindCategoryByType,
  private val saveKategori: SaveKategori,
  private val findIconByCategory: FindIconByCategory
): ViewModel() {

  private var _listIcon = MutableLiveData<List<IconModel>>()
  val listIcon: LiveData<List<IconModel>> = _listIcon

  private var _currCategory = MutableLiveData<TipeKategori>()
  val currCategory: LiveData<TipeKategori> = _currCategory

  private var _mapCategory = MutableLiveData<HashMap<TipeKategori, List<KategoriModel>>>()
  val mapCategory: LiveData<HashMap<TipeKategori, List<KategoriModel>>> = _mapCategory

  fun setCurrentCategory(tipeKategori: TipeKategori) = viewModelScope.launch {

    _currCategory.postValue(tipeKategori)

    val iconCategory = when(tipeKategori) {
      TipeKategori.PENDAPATAN -> IconCategory.INCOME
      else -> IconCategory.EXPENSE
    }

    findIconByCategory.invoke(iconCategory).collect {
      _listIcon.postValue(it)
    }
  }

  fun setCategories() = viewModelScope.launch {
    findCategoryByType.invoke().collect {
      _mapCategory.postValue(it)
    }
  }

  fun deleteCategory(kategoriModel: KategoriModel) = viewModelScope.launch {
    deleteKategori.invoke(kategoriModel)
  }

  fun updateCategory(kategoriModel: KategoriModel) = viewModelScope.launch {
    updateKategori.invoke(kategoriModel)
  }

  fun saveKategori(kategoriModel: KategoriModel) = viewModelScope.launch {
    saveKategori.invoke(kategoriModel)
  }
}