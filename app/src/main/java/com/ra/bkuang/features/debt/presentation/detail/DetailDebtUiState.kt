package com.ra.bkuang.features.debt.presentation.detail

import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.debt.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.features.debt.domain.model.HutangModel

data class DetailDebtUiState(
  val accounts: List<AkunModel> = emptyList(),
  val paidDebtRecord: List<DetailPembayaranHutangModel> = emptyList(),
  val sizeDebtRecord: Int = 0,
  val isSuccessfulDeleteDetail: Boolean? = null,
  val isSuccessfulDeleteRecord: Boolean? = null,
  val isSuccessfulUpdate: Boolean? = null,
  val isSuccessfulCreate: Boolean? = null,
  val detailDebt: HutangModel? = null,
)