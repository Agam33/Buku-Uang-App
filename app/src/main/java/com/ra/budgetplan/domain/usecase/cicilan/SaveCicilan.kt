package com.ra.budgetplan.domain.usecase.cicilan

import com.ra.budgetplan.domain.model.CicilanModel

interface SaveCicilan {
  suspend fun invoke(cicilanModel: CicilanModel)
}