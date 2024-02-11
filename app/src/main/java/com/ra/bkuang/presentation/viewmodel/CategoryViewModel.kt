package com.ra.bkuang.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ra.bkuang.data.local.entity.TipeKategori
import com.ra.bkuang.domain.model.KategoriModel
import com.ra.bkuang.domain.usecase.akun.FindCategoryByType
import com.ra.bkuang.domain.usecase.kategori.DeleteKategori
import com.ra.bkuang.domain.usecase.kategori.SaveKategori
import com.ra.bkuang.domain.usecase.kategori.UpdateKategori
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
  private val deleteKategori: DeleteKategori,
  private val updateKategori: UpdateKategori,
  private val findCategoryByType: FindCategoryByType,
  private val saveKategori: SaveKategori,
): BaseViewModel() {

  private var _currCategory = MutableLiveData<TipeKategori>()
  val currCategory: LiveData<TipeKategori> = _currCategory

  private var _mapCategory = MutableLiveData<HashMap<TipeKategori, List<KategoriModel>>>()
  val mapCategory: LiveData<HashMap<TipeKategori, List<KategoriModel>>> = _mapCategory

  fun setCurrentCategory(tipeKategori: TipeKategori) = viewModelScope.launch {
    _currCategory.postValue(tipeKategori)
  }

  fun setCategories() = viewModelScope.launch {
    findCategoryByType.invoke().collect {
      _mapCategory.postValue(it)
    }
  }

  suspend fun deleteCategory(kategoriModel: KategoriModel) =
    deleteKategori.invoke(kategoriModel)

  fun updateCategory(kategoriModel: KategoriModel) = viewModelScope.launch {
    updateKategori.invoke(kategoriModel)
  }

  fun saveKategori(kategoriModel: KategoriModel) = viewModelScope.launch {
    saveKategori.invoke(kategoriModel)
  }
}