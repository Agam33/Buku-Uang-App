package com.ra.bkuang.features.category.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.category.domain.usecase.DeleteKategoriUseCase
import com.ra.bkuang.features.category.domain.usecase.SaveKategoriUseCase
import com.ra.bkuang.features.category.domain.usecase.UpdateKategoriUseCase
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.features.category.domain.usecase.FindCategoryWithFlowUseCase
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
  private val deleteKategoriUseCase: DeleteKategoriUseCase,
  private val updateKategoriUseCase: UpdateKategoriUseCase,
  private val findCategoryWithFlowUseCase: FindCategoryWithFlowUseCase,
  private val saveKategoriUseCase: SaveKategoriUseCase,
): BaseViewModel() {

  private var _currCategory = MutableLiveData<TransactionType>()
  val currCategory: LiveData<TransactionType> = _currCategory

  private var _mapCategory = MutableLiveData<HashMap<TransactionType, List<KategoriModel>>>()
  val mapCategory: LiveData<HashMap<TransactionType, List<KategoriModel>>> = _mapCategory

  fun setCurrentCategory(transactionType: TransactionType) = viewModelScope.launch {
    _currCategory.postValue(transactionType)
  }

  fun setCategories() = viewModelScope.launch {
    findCategoryWithFlowUseCase.invoke().collect {
      _mapCategory.postValue(it)
    }
  }

  suspend fun deleteCategory(kategoriModel: KategoriModel) =
    deleteKategoriUseCase.invoke(kategoriModel)

  fun updateCategory(kategoriModel: KategoriModel) = viewModelScope.launch {
    updateKategoriUseCase.invoke(kategoriModel)
  }

  fun saveKategori(kategoriModel: KategoriModel) = viewModelScope.launch {
    saveKategoriUseCase.invoke(kategoriModel)
  }
}