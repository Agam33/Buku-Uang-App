package com.ra.budgetplan.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ra.budgetplan.domain.entity.DetailPendapatan
import com.ra.budgetplan.domain.entity.DetailPengeluaran
import com.ra.budgetplan.domain.entity.DetailTransfer
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.FindDetailPendapatanById
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.FindDetailPengeluaranById
import com.ra.budgetplan.domain.usecase.transaksi.transfer.FindDetailTransferById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
  private val findDetailTransferById: FindDetailTransferById,
  private val findDetailPendapatanById: FindDetailPendapatanById,
  private val findDetailPengeluaranById: FindDetailPengeluaranById
): BaseViewModel() {

  private var _detailPengeluaran = MutableLiveData<DetailPengeluaran>()
  val detailPengeluaran: LiveData<DetailPengeluaran> = _detailPengeluaran

  private var _detailPendapatan = MutableLiveData<DetailPendapatan>()
  val detailPendapatan: LiveData<DetailPendapatan> = _detailPendapatan

  private var _detailTransfer = MutableLiveData<DetailTransfer>()
  val detailTransfer: LiveData<DetailTransfer> = _detailTransfer

  fun getDetailPendapatan(id: UUID) {
    viewModelScope.launch {
      val income = findDetailPendapatanById.invoke(id)
      _detailPendapatan.postValue(income)
    }
  }

  fun getDetailPengeluaran(id: UUID) {
    viewModelScope.launch {
      val expense = findDetailPengeluaranById.invoke(id)
      _detailPengeluaran.postValue(expense)
    }
  }

  fun getDetailTransfer(id: UUID) {
    viewModelScope.launch {
      val transfer = findDetailTransferById.invoke(id)
      _detailTransfer.postValue(transfer)
    }
  }
}