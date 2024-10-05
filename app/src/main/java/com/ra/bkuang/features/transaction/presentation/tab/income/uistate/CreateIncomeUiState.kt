package com.ra.bkuang.features.transaction.presentation.tab.income.uistate

import com.ra.bkuang.features.account.data.model.AkunModel
import com.ra.bkuang.features.category.data.model.KategoriModel
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel

data class CreateIncomeUiState(
    val isSuccessful: Boolean? = null,

    val incomeModel: PendapatanModel? = null,

    val accountList: List<AkunModel> = emptyList(),
    val categoryList: List<KategoriModel> = emptyList(),
)
