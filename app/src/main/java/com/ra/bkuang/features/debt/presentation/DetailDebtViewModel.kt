package com.ra.bkuang.features.debt.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.debt.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.model.PembayaranHutangModel
import com.ra.bkuang.features.account.domain.usecase.FindAllAkunUseCase
import com.ra.bkuang.features.debt.domain.usecase.CancelAlarmDebtUseCase
import com.ra.bkuang.features.debt.domain.usecase.DeleteHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.DeleteRecordPembayaranHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.FindAllRecordPembayaranHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.FindHutangByIdUseCase
import com.ra.bkuang.features.debt.domain.usecase.FindHutangByIdWithFlowUseCase
import com.ra.bkuang.features.debt.domain.usecase.GetSizeListPembayaranHutangByIdUseCase
import com.ra.bkuang.features.debt.domain.usecase.SavePembayaranHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.SetAlarmDebtUseCase
import com.ra.bkuang.features.debt.domain.usecase.UpdatePembayaranHutangUseCase
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DetailDebtViewModel @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val findAllAkunUseCase: FindAllAkunUseCase,
  private val findAllRecordPembayaranHutangUseCase: FindAllRecordPembayaranHutangUseCase,
  private val savePembayaranHutangUseCase: SavePembayaranHutangUseCase,
  private val findHutangByIdWithFlowUseCase: FindHutangByIdWithFlowUseCase,
  private val findHutangByIdUseCase: FindHutangByIdUseCase,
  private val deleteRecordPembayaranHutangUseCase: DeleteRecordPembayaranHutangUseCase,
  private val updatePembayaranHutangUseCase: UpdatePembayaranHutangUseCase,
  private val getSizeListPembayaranHutangByIdUseCase: GetSizeListPembayaranHutangByIdUseCase,
  private val deleteHutangUseCase: DeleteHutangUseCase,
  private val cancelAlarmDebtUseCase: CancelAlarmDebtUseCase,
  private val setAlarmDebtUseCase: SetAlarmDebtUseCase,
): BaseViewModel() {

  private var _rvDebtRecordState = MutableLiveData<Boolean>()
  val rvDebtRecordState: LiveData<Boolean> = _rvDebtRecordState

  private var _emptyListState = MutableLiveData<Boolean>()
  val emptyListState: LiveData<Boolean> = _emptyListState

  private var _accounts = MutableLiveData<List<AkunModel>>()
  val accounts: LiveData<List<AkunModel>> = _accounts

  private var _debtRecord = MutableLiveData<ResultState<List<DetailPembayaranHutangModel>>>()
  val debtRecord: LiveData<ResultState<List<DetailPembayaranHutangModel>>> = _debtRecord

  private var _debtModel = MutableLiveData<HutangModel>()
  val debtModel: LiveData<HutangModel> get() = _debtModel

  private var _sizeListPembayaranHutang = MutableLiveData<String>()
  val sizeListPembayaranHutang: LiveData<String> = _sizeListPembayaranHutang

  private var _detailDebt: MutableStateFlow<HutangModel?> = MutableStateFlow(null)
  val detailDebt get() = _detailDebt.asStateFlow()

  suspend fun deleteHutang(hutang: HutangModel) = withContext(ioDispatcher) {
    return@withContext deleteHutangUseCase.invoke(hutang)
  }

  suspend fun setAlarmDebt(calendar: Calendar, model: HutangModel) = withContext(ioDispatcher) {
      return@withContext setAlarmDebtUseCase.invoke(calendar, model)
  }

  fun cancelAlarmDebt(model: HutangModel) {
    viewModelScope.launch {
      cancelAlarmDebtUseCase.invoke(model)
    }
  }

  fun setState(rvState: Boolean, emptyState: Boolean) {
    _rvDebtRecordState.postValue(rvState)
    _emptyListState.postValue(emptyState)
  }

  fun getSizeListPembayaranHutang(id: String) {
    viewModelScope.launch {
      getSizeListPembayaranHutangByIdUseCase.invoke(id).collect {
        _sizeListPembayaranHutang.postValue("${it ?: 0}")
      }
    }
  }

  fun getAllAccount() {
    viewModelScope.launch {
      _accounts.postValue(findAllAkunUseCase.invoke())
    }
  }

  suspend fun getHutangById(id: String): HutangModel = withContext(ioDispatcher) {
    return@withContext findHutangByIdUseCase.invoke(id)
  }

  fun getHutangByIdWithFlow(id: String) = findHutangByIdWithFlowUseCase.invoke(id)

  suspend fun savePembayaranHutang(pembayaranHutangModel: PembayaranHutangModel) =
    savePembayaranHutangUseCase.invoke(pembayaranHutangModel)

  suspend fun deleteRecordPembayaranHutang(detailPembayaranHutangModel: DetailPembayaranHutangModel) =
    deleteRecordPembayaranHutangUseCase.invoke(detailPembayaranHutangModel)

  suspend fun updatePembayaranHutang(newModel: PembayaranHutangModel, oldModel: PembayaranHutangModel) =
    updatePembayaranHutangUseCase.invoke(newModel, oldModel)

  fun getAllDebtRecord(id: String) {
    viewModelScope.launch {
      _debtRecord.postValue(findAllRecordPembayaranHutangUseCase.invoke(id))
    }
  }
}