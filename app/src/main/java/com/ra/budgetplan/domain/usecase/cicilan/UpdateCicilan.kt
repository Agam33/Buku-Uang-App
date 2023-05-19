package com.ra.budgetplan.domain.usecase.cicilan

import com.ra.budgetplan.domain.model.CicilanModel

interface UpdateCicilan {
  suspend fun invoke(cicilanModel: CicilanModel)
}