package com.ra.bkuang.features.transaction.presentation.tab.transfer.uistate

import com.ra.bkuang.core.data.source.local.database.entity.DetailTransfer

data class TransferFragmentUiState(
    val transferList: List<DetailTransfer> = emptyList(),
    val isSuccessful: Boolean? = null
)
