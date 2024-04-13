package com.ra.bkuang.features.category.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.account.domain.usecase.FindCategoryByType
import com.ra.bkuang.features.category.domain.usecase.DeleteKategori
import com.ra.bkuang.features.category.domain.usecase.SaveKategori
import com.ra.bkuang.features.category.domain.usecase.UpdateKategori
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.features.transaction.data.entity.TransactionType
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

  private var _currCategory = MutableLiveData<TransactionType>()
  val currCategory: LiveData<TransactionType> = _currCategory

  private var _mapCategory = MutableLiveData<HashMap<TransactionType, List<KategoriModel>>>()
  val mapCategory: LiveData<HashMap<TransactionType, List<KategoriModel>>> = _mapCategory

  fun setCurrentCategory(transactionType: TransactionType) = viewModelScope.launch {
    _currCategory.postValue(transactionType)
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