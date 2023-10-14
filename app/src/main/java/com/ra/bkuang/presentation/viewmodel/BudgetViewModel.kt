package com.ra.bkuang.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ra.bkuang.domain.entity.DetailBudget
import com.ra.bkuang.domain.entity.TipeKategori
import com.ra.bkuang.domain.model.BudgetModel
import com.ra.bkuang.domain.model.KategoriModel
import com.ra.bkuang.domain.usecase.akun.FindCategoryByType
import com.ra.bkuang.domain.usecase.budget.CreateBudget
import com.ra.bkuang.domain.usecase.budget.DeleteBudgetById
import com.ra.bkuang.domain.usecase.budget.EditBudget
import com.ra.bkuang.domain.usecase.budget.FindAllBudgetByDate
import com.ra.bkuang.domain.usecase.budget.FindBudgetById
import com.ra.bkuang.util.Resource
import com.ra.bkuang.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
  private val findAllBudgetByDate: FindAllBudgetByDate,
  private val editBudget: EditBudget,
  private val deleteBudgetById: DeleteBudgetById,
  private val createBudget: CreateBudget,
  private val findKategoriByType: FindCategoryByType,
  private val findBudgetById: FindBudgetById
): BaseViewModel() {

  private var _listBudget = MutableLiveData<List<DetailBudget>>()
  val listBudget: LiveData<List<DetailBudget>> get() = _listBudget

  private var _listCategoryByType = MutableLiveData<List<KategoriModel>>()
  val listCategoryByType: LiveData<List<KategoriModel>> get() = _listCategoryByType

  private var _budgetModel = MutableLiveData<BudgetModel>()
  val budgetModel: LiveData<BudgetModel> get() = _budgetModel

  fun findBudgetById(id: UUID) {
    viewModelScope.launch {
      val budget = findBudgetById.invoke(id)
      _budgetModel.postValue(budget)
    }
  }

  fun findAllBudget(fromDate: LocalDate, toDate: LocalDate) {
    viewModelScope.launch {
      val budgets = findAllBudgetByDate.invoke(fromDate, toDate)
      _listBudget.postValue(budgets)
    }
  }

  fun setCategoryByType(tipeKategori: TipeKategori) = viewModelScope.launch {
    findKategoriByType.invoke(tipeKategori).collect {
      when (it) {
        is Resource.Empty -> {}
        is Resource.Success -> {
          _listCategoryByType.postValue(it.data ?: mutableListOf())
        }
        else -> {}
      }
    }
  }

  fun deleteBudgetById(id: UUID): Flow<ResourceState> =
    deleteBudgetById.invoke(id)

  fun updateBudget(budgetModel: BudgetModel): Flow<ResourceState> =
    editBudget.invoke(budgetModel)

  fun createBudget(budgetModel: BudgetModel): Flow<ResourceState> =
    createBudget.invoke(
      budgetModel.bulanTahun,
      budgetModel.bulanTahun,
      budgetModel
    )
}