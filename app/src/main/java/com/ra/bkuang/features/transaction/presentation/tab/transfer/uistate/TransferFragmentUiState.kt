package com.ra.bkuang.features.transaction.presentation.tab.transfer.uistate

import com.ra.bkuang.features.transaction.data.entity.DetailTransfer

data class TransferFragmentUiState(
  val transferList: List<DetailTransfer> = emptyList(),
  val isSuccessful: Boolean? = null
)
