package com.ra.bkuang.features.transaction.presentation.tab.transfer.uistate

import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.transaction.domain.model.TransferModel

data class CreateTransferFragmentUiState(
  val isSuccessful: Boolean? = null,

  val isSave: Boolean = false,

  val showSaveAlert: Boolean = false,

  val transferModel: TransferModel? = null,
  val accountList: List<AkunModel> = emptyList(),
)
