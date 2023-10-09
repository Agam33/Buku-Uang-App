package com.ra.budgetplan.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ra.budgetplan.domain.model.AkunModel
import com.ra.budgetplan.domain.model.DetailPembayaranHutangModel
import com.ra.budgetplan.domain.model.HutangModel
import com.ra.budgetplan.domain.model.PembayaranHutangModel
import com.ra.budgetplan.domain.usecase.akun.FindAllAkun
import com.ra.budgetplan.domain.usecase.hutang.DeleteRecordPembayaranHutang
import com.ra.budgetplan.domain.usecase.hutang.FindAllRecordPembayaranHutang
import com.ra.budgetplan.domain.usecase.hutang.FindHutangById
import com.ra.budgetplan.domain.usecase.hutang.FindHutangByIdWithFlow
import com.ra.budgetplan.domain.usecase.hutang.GetSizeListPembayaranHutangById
import com.ra.budgetplan.domain.usecase.hutang.SavePembayaranHutang
import com.ra.budgetplan.domain.usecase.hutang.UpdatePembayaranHutang
import com.ra.budgetplan.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DetailDebtViewModel @Inject constructor(
  private val findAllAkun: FindAllAkun,
  private val findAllRecordPembayaranHutang: FindAllRecordPembayaranHutang,
  private val savePembayaranHutang: SavePembayaranHutang,
  private val findHutangByIdWithFlow: FindHutangByIdWithFlow,
  private val findHutangById: FindHutangById,
  private val deleteRecordPembayaranHutang: DeleteRecordPembayaranHutang,
  private val updatePembayaranHutang: UpdatePembayaranHutang,
  private val getSizeListPembayaranHutangById: GetSizeListPembayaranHutangById
): BaseViewModel() {

  private var _rvDebtRecordState = MutableLiveData<Boolean>()
  val rvDebtRecordState: LiveData<Boolean> = _rvDebtRecordState

  private var _emptyListState = MutableLiveData<Boolean>()
  val emptyListState: LiveData<Boolean> = _emptyListState

  private var _accounts = MutableLiveData<List<AkunModel>>()
  val accounts: LiveData<List<AkunModel>> = _accounts

  private var _debtRecord = MutableLiveData<Resource<List<DetailPembayaranHutangModel>>>()
  val debtRecord: LiveData<Resource<List<DetailPembayaranHutangModel>>> = _debtRecord

  private var _debtModel = MutableLiveData<HutangModel>()
  val debtModel: LiveData<HutangModel> get() = _debtModel

  private var _sizeListPembayaranHutang = MutableLiveData<String>()
  val sizeListPembayaranHutang: LiveData<String> = _sizeListPembayaranHutang

  fun setState(rvState: Boolean, emptyState: Boolean) {
    _rvDebtRecordState.postValue(rvState)
    _emptyListState.postValue(emptyState)
  }

  fun getSizeListPembayaranHutang(id: UUID) {
    viewModelScope.launch {
      getSizeListPembayaranHutangById.invoke(id).collect {
        _sizeListPembayaranHutang.postValue("${it ?: 0}")
      }
    }
  }

  fun getAllAccount() {
    viewModelScope.launch {
      _accounts.postValue(findAllAkun.invoke())
    }
  }

  fun getHutangById(id: UUID) {
    viewModelScope.launch {
      val data = findHutangById.invoke(id)
      _debtModel.postValue(data)
    }
  }

  fun getHutangByIdWithFlow(id: UUID) =
    findHutangByIdWithFlow.invoke(id)

  suspend fun savePembayaranHutang(pembayaranHutangModel: PembayaranHutangModel) =
    savePembayaranHutang.invoke(pembayaranHutangModel)

  suspend fun deleteRecordPembayaranHutang(detailPembayaranHutangModel: DetailPembayaranHutangModel) =
    deleteRecordPembayaranHutang.invoke(detailPembayaranHutangModel)

  suspend fun updatePembayaranHutang(newModel: PembayaranHutangModel, oldModel: PembayaranHutangModel) =
    updatePembayaranHutang.invoke(newModel, oldModel)

  fun getAllDebtRecord(id: UUID) {
    viewModelScope.launch {
      _debtRecord.postValue(findAllRecordPembayaranHutang.invoke(id))
    }
  }
}